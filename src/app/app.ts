import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from './services/user';
import { ItemService, Item } from './services/item';

interface ClaimRecord {
  id: number;
  itemId: number | null;
  itemName: string;
  reason: string;
  proof: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  userId: number | null;
  userName: string;
  ownerUserId: number | null;
  ownerName: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  // =========================================================
  // AUTH
  // =========================================================

  email = '';
  password = '';
  name = '';
  newPasswordReset = '';

  showPassword = false;
  message = '';

  isRegisterMode = false;
  isForgotPasswordMode = false;
  isLoading = false;
  isLoggedIn = false;

  currentUserName = '';
  currentUserId: number | null = null;


  // =========================================================
  // LOST & FOUND
  // =========================================================

  items: Item[] = [];
  isLoadingItems = false;

  activeTab:
    | 'FEED'
    | 'REPORT_LOST'
    | 'REPORT_FOUND'
    | 'MY_CLAIMS'
    | 'CLAIMS_RECEIVED'
    = 'FEED';


  // Filters

  filterType = '';
  filterCategory = '';
  searchQuery = '';


  // =========================================================
  // NEW ITEM FORM
  // =========================================================

  newItemName = '';
  newItemCategory = 'Electronics';
  newItemLocation = '';
  newItemDescription = '';
  newItemImageUrl = '';

  isCompressingPhoto = false;
  itemFormMessage = '';
  isSubmittingItem = false;


  // =========================================================
  // EDIT ITEM
  // =========================================================

  editingItem: Item | null = null;

  editName = '';
  editCategory = 'Electronics';
  editLocation = '';
  editDescription = '';
  editImageUrl = '';

  isCompressingEditPhoto = false;
  isSavingEdit = false;
  editMessage = '';


  // =========================================================
  // CLAIM SYSTEM
  // =========================================================

  claimingItem: Item | null = null;

  claimReason = '';
  claimProof = '';
  claimMessage = '';

  claims: ClaimRecord[] = [];


  constructor(
    private userService: UserService,
    private itemService: ItemService,
    private cdr: ChangeDetectorRef
  ) { }


  // =========================================================
  // STARTUP
  // =========================================================

  ngOnInit(): void {

    const savedUser = localStorage.getItem('findit_user');

    if (savedUser) {
      try {
        const user = JSON.parse(savedUser);

        this.currentUserId = user.id || null;
        this.currentUserName = user.name || '';
        this.email = user.email || '';
        this.isLoggedIn = true;

      } catch {
        localStorage.removeItem('findit_user');
      }
    }


    const savedClaims = localStorage.getItem('findit_claims');

    if (savedClaims) {
      try {
        this.claims = JSON.parse(savedClaims);
      } catch {
        this.claims = [];
      }
    }


    this.loadItems();
  }


  // =========================================================
  // AUTH
  // =========================================================

  togglePassword(): void {
    this.showPassword = !this.showPassword;
    this.cdr.detectChanges();
  }


  onSubmit(event: Event): void {

    event.preventDefault();

    this.message = '';
    this.isLoading = true;

    this.cdr.detectChanges();


    if (this.isRegisterMode) {

      this.userService.register({
        name: this.name.trim(),
        email: this.email.trim(),
        password: this.password

      }).subscribe({

        next: (response) => {

          this.message =
            response.message ||
            'Account created successfully!';

          this.isLoading = false;

          this.currentUserName =
            response.name ||
            this.name.trim();

          this.currentUserId =
            response.id ||
            null;

          this.isLoggedIn = true;


          localStorage.setItem(
            'findit_user',
            JSON.stringify({
              id: this.currentUserId,
              name: this.currentUserName,
              email: this.email.trim()
            })
          );


          this.cdr.detectChanges();

          this.loadItems();
        },


        error: (error) => {

          this.message =
            error.error?.message ||
            'Registration failed. Check that the backend is running.';

          this.isLoading = false;

          this.cdr.detectChanges();
        }

      });

    } else {

      this.userService.login({
        email: this.email.trim(),
        password: this.password

      }).subscribe({

        next: (response) => {

          this.message =
            response.message ||
            'Login successful.';

          this.isLoading = false;

          this.currentUserId =
            response.id ||
            null;

          this.currentUserName =
            response.name ||
            this.email.split('@')[0];

          this.isLoggedIn = true;


          localStorage.setItem(
            'findit_user',
            JSON.stringify({
              id: this.currentUserId,
              name: this.currentUserName,
              email: this.email.trim()
            })
          );


          this.cdr.detectChanges();

          this.loadItems();
        },


        error: (error) => {

          this.message =
            error.error?.message ||
            'Login failed. Invalid password or account does not exist.';

          this.isLoading = false;

          this.cdr.detectChanges();
        }

      });

    }
  }


  logout(): void {

    localStorage.removeItem('findit_user');

    this.isLoggedIn = false;

    this.currentUserId = null;
    this.currentUserName = '';

    this.password = '';
    this.message = '';

    this.activeTab = 'FEED';

    this.closeClaimModal();
    this.closeEditModal();

    this.cdr.detectChanges();
  }


  onForgotPassword(event: Event): void {

    event.preventDefault();

    this.isForgotPasswordMode = true;
    this.isRegisterMode = false;

    this.message = '';
    this.newPasswordReset = '';

    this.cdr.detectChanges();
  }


  backToLogin(): void {

    this.isForgotPasswordMode = false;
    this.isRegisterMode = false;

    this.message = '';

    this.cdr.detectChanges();
  }


  onCreateAccount(): void {

    this.isRegisterMode = !this.isRegisterMode;
    this.isForgotPasswordMode = false;

    this.message = '';
    this.password = '';

    this.cdr.detectChanges();
  }


  submitPasswordReset(): void {

    if (!this.email.trim() || !this.newPasswordReset) {

      this.message =
        'Please provide your email and new password.';

      this.cdr.detectChanges();

      return;
    }


    this.isLoading = true;
    this.message = '';

    this.cdr.detectChanges();


    this.userService
      .resetPassword(
        this.email.trim(),
        this.newPasswordReset
      )
      .subscribe({

        next: (response) => {

          this.message =
            response.message ||
            'Password reset successfully. You can now sign in.';

          this.isLoading = false;
          this.isForgotPasswordMode = false;

          this.password =
            this.newPasswordReset;

          this.newPasswordReset = '';

          this.cdr.detectChanges();
        },


        error: (error) => {

          this.message =
            error.error?.message ||
            'Password reset failed. Make sure your email is registered.';

          this.isLoading = false;

          this.cdr.detectChanges();
        }

      });
  }


  // =========================================================
  // ITEMS
  // =========================================================

  loadItems(): void {

    this.isLoadingItems = true;

    this.cdr.detectChanges();


    this.itemService.getItems({

      type:
        this.filterType ||
        undefined,

      category:
        this.filterCategory ||
        undefined,

      search:
        this.searchQuery ||
        undefined

    }).subscribe({

      next: (data) => {

        this.items = data;
        this.isLoadingItems = false;

        this.cdr.detectChanges();
      },


      error: () => {

        this.isLoadingItems = false;

        this.cdr.detectChanges();
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


  // =========================================================
  // NEW ITEM PHOTO
  // =========================================================

  onFileSelected(event: Event): void {

    const input =
      event.target as HTMLInputElement;


    if (!input.files || input.files.length === 0) {
      return;
    }


    const file = input.files[0];

    this.isCompressingPhoto = true;

    this.cdr.detectChanges();


    const reader = new FileReader();


    reader.onload = (event) => {

      const image = new Image();


      image.onload = () => {

        const maxDimension = 800;

        let width = image.width;
        let height = image.height;


        if (
          width > maxDimension ||
          height > maxDimension
        ) {

          if (width > height) {

            height =
              Math.round(
                (height * maxDimension) /
                width
              );

            width = maxDimension;

          } else {

            width =
              Math.round(
                (width * maxDimension) /
                height
              );

            height = maxDimension;
          }
        }


        const canvas =
          document.createElement('canvas');

        canvas.width = width;
        canvas.height = height;


        const context =
          canvas.getContext('2d');


        if (context) {

          context.drawImage(
            image,
            0,
            0,
            width,
            height
          );

          this.newItemImageUrl =
            canvas.toDataURL(
              'image/jpeg',
              0.8
            );

        } else {

          this.newItemImageUrl =
            event.target?.result as string;
        }


        this.isCompressingPhoto = false;

        this.cdr.detectChanges();
      };


      image.src =
        event.target?.result as string;
    };


    reader.readAsDataURL(file);
  }


  removePhoto(): void {

    this.newItemImageUrl = '';

    this.cdr.detectChanges();
  }


  // =========================================================
  // REPORT ITEM
  // =========================================================

  submitReport(type: 'LOST' | 'FOUND'): void {

    if (
      !this.newItemName.trim() ||
      !this.newItemLocation.trim()
    ) {

      this.itemFormMessage =
        'Please enter item title and location.';

      this.cdr.detectChanges();

      return;
    }


    this.isSubmittingItem = true;
    this.itemFormMessage = '';

    this.cdr.detectChanges();


    const payload: Partial<Item> = {

      itemName:
        this.newItemName.trim(),

      category:
        this.newItemCategory,

      location:
        this.newItemLocation.trim(),

      description:
        this.newItemDescription.trim(),

      imageUrl:
        this.newItemImageUrl.trim(),

      user:
        this.currentUserId
          ? { id: this.currentUserId }
          : undefined
    };


    const request$ =
      type === 'LOST'
        ? this.itemService.reportLost(payload)
        : this.itemService.reportFound(payload);


    request$.subscribe({

      next: () => {

        this.isSubmittingItem = false;
        this.itemFormMessage = '';

        this.activeTab = 'FEED';

        this.newItemName = '';
        this.newItemLocation = '';
        this.newItemDescription = '';
        this.newItemImageUrl = '';

        this.cdr.detectChanges();

        this.loadItems();
      },


      error: () => {

        this.isSubmittingItem = false;

        this.itemFormMessage =
          'Failed to submit report. Ensure backend is running.';

        this.cdr.detectChanges();
      }

    });
  }


  markReturned(item: Item): void {

    const id =
      item.itemId ||
      item.id;


    if (!id) {
      return;
    }


    this.itemService
      .updateStatus(
        id,
        'RETURNED'
      )
      .subscribe({

        next: (updated) => {

          item.status =
            updated.status;

          this.cdr.detectChanges();
        }

      });
  }


  // =========================================================
  // CLAIM SYSTEM
  // =========================================================

  get myClaims(): ClaimRecord[] {

    return this.claims.filter(
      claim =>
        claim.userId ===
        this.currentUserId
    );
  }


  get receivedClaims(): ClaimRecord[] {

    return this.claims.filter(
      claim =>
        claim.ownerUserId ===
        this.currentUserId
    );
  }


  hasClaimed(item: Item): boolean {

    const itemId =
      item.itemId ||
      item.id;


    return this.claims.some(
      claim =>
        claim.itemId === itemId &&
        claim.userId === this.currentUserId &&
        claim.status !== 'REJECTED'
    );
  }


  openClaimModal(item: Item): void {

    this.claimingItem = item;

    this.claimReason = '';
    this.claimProof = '';
    this.claimMessage = '';

    this.cdr.detectChanges();
  }


  closeClaimModal(): void {

    this.claimingItem = null;

    this.claimReason = '';
    this.claimProof = '';
    this.claimMessage = '';

    this.cdr.detectChanges();
  }


  submitClaim(): void {

    if (!this.claimingItem) {
      return;
    }


    if (
      !this.claimReason.trim() ||
      !this.claimProof.trim()
    ) {

      this.claimMessage =
        'Please fill in both fields.';

      this.cdr.detectChanges();

      return;
    }


    if (this.hasClaimed(this.claimingItem)) {

      this.claimMessage =
        'You have already submitted a claim for this item.';

      this.cdr.detectChanges();

      return;
    }


    const itemId =
      this.claimingItem.itemId ||
      this.claimingItem.id ||
      null;


    const newClaim: ClaimRecord = {

      id:
        Date.now(),

      itemId,

      itemName:
        this.claimingItem.itemName ||
        this.claimingItem.title ||
        'Unknown Item',

      reason:
        this.claimReason.trim(),

      proof:
        this.claimProof.trim(),

      status:
        'PENDING',

      userId:
        this.currentUserId,

      userName:
        this.currentUserName ||
        this.email,

      ownerUserId:
        this.claimingItem.user?.id ||
        null,

      ownerName:
        this.claimingItem.user?.name ||
        this.claimingItem.user?.email ||
        'Unknown'
    };


    this.claims.push(newClaim);


    this.saveClaims();


    this.closeClaimModal();


    this.activeTab =
      'MY_CLAIMS';


    this.cdr.detectChanges();
  }


  updateClaimStatus(
    claimId: number,
    status: 'APPROVED' | 'REJECTED'
  ): void {

    const claim =
      this.claims.find(
        claim =>
          claim.id === claimId
      );


    if (!claim) {
      return;
    }


    claim.status = status;


    this.saveClaims();


    this.cdr.detectChanges();
  }


  saveClaims(): void {

    localStorage.setItem(
      'findit_claims',
      JSON.stringify(this.claims)
    );
  }


  // =========================================================
  // EDIT ITEM
  // =========================================================

  openEditModal(item: Item): void {

    this.editingItem = item;

    this.editName =
      item.itemName ||
      item.title ||
      '';

    this.editCategory =
      item.category ||
      'Electronics';

    this.editLocation =
      item.location ||
      '';

    this.editDescription =
      item.description ||
      '';

    this.editImageUrl =
      item.imageUrl ||
      item.photo ||
      '';

    this.editMessage = '';

    this.cdr.detectChanges();
  }


  closeEditModal(): void {

    this.editingItem = null;

    this.editMessage = '';

    this.cdr.detectChanges();
  }


  onEditFileSelected(event: Event): void {

    const input =
      event.target as HTMLInputElement;


    if (!input.files || input.files.length === 0) {
      return;
    }


    const file =
      input.files[0];

    this.isCompressingEditPhoto = true;

    this.cdr.detectChanges();


    const reader =
      new FileReader();


    reader.onload = (event) => {

      const image =
        new Image();


      image.onload = () => {

        const maxDimension =
          800;

        let width =
          image.width;

        let height =
          image.height;


        if (
          width > maxDimension ||
          height > maxDimension
        ) {

          if (width > height) {

            height =
              Math.round(
                (height * maxDimension) /
                width
              );

            width =
              maxDimension;

          } else {

            width =
              Math.round(
                (width * maxDimension) /
                height
              );

            height =
              maxDimension;
          }
        }


        const canvas =
          document.createElement(
            'canvas'
          );

        canvas.width =
          width;

        canvas.height =
          height;


        const context =
          canvas.getContext('2d');


        if (context) {

          context.drawImage(
            image,
            0,
            0,
            width,
            height
          );


          this.editImageUrl =
            canvas.toDataURL(
              'image/jpeg',
              0.8
            );

        } else {

          this.editImageUrl =
            event.target?.result as string;
        }


        this.isCompressingEditPhoto =
          false;

        this.cdr.detectChanges();
      };


      image.src =
        event.target?.result as string;
    };


    reader.readAsDataURL(file);
  }


  removeEditPhoto(): void {

    this.editImageUrl = '';

    this.cdr.detectChanges();
  }


  isMyItem(item: Item): boolean {

    if (!this.isLoggedIn) {
      return false;
    }


    if (
      this.currentUserId &&
      item.user?.id
    ) {

      return (
        item.user.id ===
        this.currentUserId
      );
    }


    if (
      this.email &&
      item.user?.email
    ) {

      return (
        item.user.email.toLowerCase() ===
        this.email.trim().toLowerCase()
      );
    }


    return false;
  }


  saveItemChanges(): void {

    if (!this.editingItem) {
      return;
    }


    const id =
      this.editingItem.itemId ||
      this.editingItem.id;


    if (!id) {
      return;
    }


    if (
      !this.editName.trim() ||
      !this.editLocation.trim()
    ) {

      this.editMessage =
        'Item name and location cannot be empty.';

      this.cdr.detectChanges();

      return;
    }


    this.isSavingEdit = true;
    this.editMessage = '';

    this.cdr.detectChanges();


    const updatedData:
      Partial<Item> = {

      itemName:
        this.editName.trim(),

      category:
        this.editCategory,

      location:
        this.editLocation.trim(),

      description:
        this.editDescription.trim(),

      imageUrl:
        this.editImageUrl.trim()
    };


    this.itemService
      .updateItem(
        id,
        updatedData,
        this.currentUserId ||
        undefined
      )
      .subscribe({

        next: (saved) => {

          if (this.editingItem) {

            this.editingItem.itemName =
              saved.itemName;

            this.editingItem.category =
              saved.category;

            this.editingItem.location =
              saved.location;

            this.editingItem.description =
              saved.description;

            this.editingItem.imageUrl =
              saved.imageUrl;
          }


          this.isSavingEdit = false;

          this.closeEditModal();

          this.loadItems();
        },


        error: (error) => {

          this.isSavingEdit = false;

          this.editMessage =
            error.error?.message ||
            'Failed to save changes. Permission denied or backend offline.';

          this.cdr.detectChanges();
        }

      });
  }


  deleteItem(item: Item): void {

    const id =
      item.itemId ||
      item.id;


    if (!id) {
      return;
    }


    if (
      !confirm(
        `Are you sure you want to delete "${item.itemName}"?`
      )
    ) {
      return;
    }


    this.itemService
      .deleteItem(
        id,
        this.currentUserId ||
        undefined
      )
      .subscribe({

        next: () => {

          this.items =
            this.items.filter(
              existingItem =>
                (
                  existingItem.itemId ||
                  existingItem.id
                ) !== id
            );


          this.cdr.detectChanges();
        },


        error: (error) => {

          alert(
            error.error?.message ||
            'Failed to delete item. You can only delete items you reported.'
          );
        }

      });
  }
}
