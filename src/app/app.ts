import { Component } from '@angular/core';
import { UserService } from './services/user';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  email = '';
  password = '';
  name = '';
  showPassword = false;
  message = '';
  isRegisterMode = false;
  isLoading = false;

  constructor(private userService: UserService) {}

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
          this.message = response.message;
          this.isLoading = false;
          this.isRegisterMode = false;
          this.password = '';
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
          this.message = response.message;
          this.isLoading = false;
        },
        error: (error) => {
          this.message =
            error.error?.message ||
            'Login failed. Check your connection and try again.';
          this.isLoading = false;
        }
      });
    }
  }

  onForgotPassword(event: Event): void {
    event.preventDefault();
    this.message =
      'Password recovery is not available yet.';
  }

  onCreateAccount(): void {
    this.isRegisterMode = !this.isRegisterMode;
    this.message = '';
    this.password = '';
  }
}