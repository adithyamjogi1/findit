import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from './services/user';
import { ItemService, Item } from './services/item';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  // Member 1 - Auth Fields
  email = '';
  password = '';
  name = '';
  showPassword = false;
  message = '';
  isRegisterMode = false;
  isLoading = false;
  isLoggedIn = false;
  currentUserName = '';
  currentUserId: number | null = null;

  // Member 2 - Lost & Found Fields
  items: Item[] = [];
  isLoadingItems = false;
  activeTab: 'FEED' | 'REPORT_LOST' | 'REPORT_FOUND' = 'FEED';
  
  // Filter state
  filterType = '';
  filterCategory = '';
  searchQuery = '';

  // New item form fields
  newItemName = '';
  newItemCategory = 'Electronics';
  newItemLocation = '';
  newItemDescription = '';
  newItemImageUrl = '';
  itemFormMessage = '';
  isSubmittingItem = false;

  constructor(
    private userService: UserService,
    private itemService: ItemService
  ) {}

  ngOnInit(): void {
    // If backend already has items, load them
    this.loadItems();
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  onSubmit(event: Event): void {
    event.preventDefault();
    this.message = '';
    this.isLoading = true;

    if (this.isRegisterMode) {
      this.userService.register({
        name: this.name.trim(),
        email: this.email.trim(),
        password: this.password
      }).subscribe({
        next: (response) => {
          this.message = response.message || 'Account created successfully!';
          this.isLoading = false;
          this.currentUserName = response.name || this.name.trim();
          this.currentUserId = response.id || null;
          this.isLoggedIn = true;
          this.loadItems();
        },
        error: (error) => {
          this.message =
            error.error?.message ||
            'Registration failed. Check that the backend is running.';
          this.isLoading = false;
        }
      });
    } else {
      this.userService.login({
        email: this.email.trim(),
        password: this.password
      }).subscribe({
        next: (response) => {
          this.message = response.message || 'Login successful.';
          this.isLoading = false;
          this.currentUserName = this.email.split('@')[0];
          this.isLoggedIn = true;
          this.loadItems();
        },
        error: (error) => {
          this.message =
            error.error?.message ||
            'Login failed. Check your connection or create an account.';
          this.isLoading = false;
        }
      });
    }
  }

  logout(): void {
    this.isLoggedIn = false;
    this.message = '';
    this.password = '';
  }

  onForgotPassword(event: Event): void {
    event.preventDefault();
    this.message = 'Password recovery is not available yet.';
  }

  onCreateAccount(): void {
    this.isRegisterMode = !this.isRegisterMode;
    this.message = '';
    this.password = '';
  }

  // --- Member 2 Methods ---

  loadItems(): void {
    this.isLoadingItems = true;
    this.itemService.getItems({
      type: this.filterType || undefined,
      category: this.filterCategory || undefined,
      search: this.searchQuery || undefined
    }).subscribe({
      next: (data) => {
        this.items = data;
        this.isLoadingItems = false;
      },
      error: () => {
        this.isLoadingItems = false;
      }
    });
  }

  setFilterType(type: string): void {
    this.filterType = type;
    this.loadItems();
  }

  setFilterCategory(category: string): void {
    this.filterCategory = category;
    this.loadItems();
  }

  onSearch(): void {
    this.loadItems();
  }

  submitReport(type: 'LOST' | 'FOUND'): void {
    if (!this.newItemName.trim() || !this.newItemLocation.trim()) {
      this.itemFormMessage = 'Please enter item title and location.';
      return;
    }

    this.isSubmittingItem = true;
    this.itemFormMessage = '';

    const payload: Partial<Item> = {
      itemName: this.newItemName.trim(),
      category: this.newItemCategory,
      location: this.newItemLocation.trim(),
      description: this.newItemDescription.trim(),
      imageUrl: this.newItemImageUrl.trim(),
      user: this.currentUserId ? { id: this.currentUserId } : undefined
    };

    const request$ = type === 'LOST' 
      ? this.itemService.reportLost(payload)
      : this.itemService.reportFound(payload);

    request$.subscribe({
      next: () => {
        this.isSubmittingItem = false;
        this.itemFormMessage = '';
        this.activeTab = 'FEED';
        // Reset form
        this.newItemName = '';
        this.newItemLocation = '';
        this.newItemDescription = '';
        this.newItemImageUrl = '';
        this.loadItems();
      },
      error: () => {
        this.isSubmittingItem = false;
        this.itemFormMessage = 'Failed to submit report. Ensure backend is running.';
      }
    });
  }

  markReturned(item: Item): void {
    const id = item.itemId || item.id;
    if (!id) return;

    this.itemService.updateStatus(id, 'RETURNED').subscribe({
      next: (updated) => {
        item.status = updated.status;
      }
    });
  }
}