package com.findit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
                this.userService = userService;
        }

        @PostMapping("/register")

        public ResponseEntity<?> register(
                        @RequestBody Map<String, String> request) {
                try {
                        String name = request.get("name");
                        String email = request.get("email");
                        String password = request.get("password");

                        if (name == null || name.isBlank()
                                        || email == null || email.isBlank()
                                        || password == null || password.isBlank()) {
                                return ResponseEntity.badRequest()
                                                .body(Map.of("message",
                                                                "Name, email, and password are required."));
                        }

                        User user = userService.registerUser(
                                        name, email, password);

                        return ResponseEntity.status(HttpStatus.CREATED)
                                        .body(Map.of(
                                                        "message", "Account created successfully.",
                                                        "id", user.getId(),
                                                        "name", user.getName(),
                                                        "email", user.getEmail()));

                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                        .body(Map.of("message", e.getMessage()));
                }
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(
                        @RequestBody Map<String, String> request) {

                String email = request.get("email");
                String password = request.get("password");

                if (email == null || email.isBlank()
                                || password == null || password.isBlank()) {
                        return ResponseEntity.badRequest()
                                        .body(Map.of("message",
                                                        "Email and password are required."));
                }
                User user = userService.authenticateUser(email, password).orElse(null);
                if (user == null) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(Map.of("message", "Invalid email or password."));
                }

                return ResponseEntity.ok(Map.of(
                                "message", "Login successful.",
                                "id", user.getId(),
                                "name", user.getName(),
                                "email", user.getEmail()));
        }

        @PostMapping("/reset-password")
        public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
                String email = request.get("email");
                String newPassword = request.get("newPassword");

                if (email == null || email.isBlank() || newPassword == null || newPassword.isBlank()) {
                        return ResponseEntity.badRequest()
                                        .body(Map.of("message", "Email and newPassword are required."));
                }

                boolean updated = userService.resetPassword(email, newPassword);
                if (!updated) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(Map.of("message", "No account registered with this email address."));
                }

                return ResponseEntity.ok(
                                Map.of("message", "Password reset successfully! You can now sign in with your new password."));
        }
}
