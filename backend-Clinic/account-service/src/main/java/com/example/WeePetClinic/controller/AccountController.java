package main.java.com.example.WeePetClinic.controller;

import main.java.com.example.WeePetClinic.model.UserAccount;
import main.java.com.example.WeePetClinic.service.AccountService;
import main.java.com.example.WeePetClinic.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    // Public endpoints
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            UserAccount createdAccount = accountService.registerUser(registrationDto);
            Map<String, Object> response = Map.of(
                "message", "User registered successfully",
                "userId", createdAccount.getId(),
                "username", createdAccount.getUsername(),
                "email", createdAccount.getEmail()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody UserLoginDto loginDto) {
        try {
            LoginResponseDto loginResponse = accountService.loginUser(loginDto);
            return ResponseEntity.ok(loginResponse.toMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody TokenRefreshDto refreshDto) {
        try {
            LoginResponseDto newTokens = accountService.refreshToken(refreshDto.getRefreshToken());
            return ResponseEntity.ok(newTokens.toMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        try {
            accountService.sendPasswordResetEmail(forgotPasswordDto.getEmail());
            return ResponseEntity.ok(Map.of("message", "Password reset email sent"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody PasswordResetDto resetDto) {
        try {
            accountService.resetPassword(resetDto.getToken(), resetDto.getNewPassword());
            return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody EmailVerificationDto verificationDto) {
        try {
            accountService.verifyEmail(verificationDto.getToken());
            return ResponseEntity.ok(Map.of("message", "Email verified successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Protected endpoints (require authentication)
    
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserAccount> getCurrentUserProfile() {
        UserAccount profile = accountService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }
    
    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserAccount> updateProfile(@Valid @RequestBody ProfileUpdateDto profileDto) {
        UserAccount updatedProfile = accountService.updateProfile(profileDto);
        return ResponseEntity.ok(updatedProfile);
    }
    
    @PutMapping("/change-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> changePassword(@Valid @RequestBody PasswordChangeDto passwordDto) {
        try {
            accountService.changePassword(passwordDto.getCurrentPassword(), passwordDto.getNewPassword());
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> logoutUser() {
        accountService.logoutUser();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
    
    @PostMapping("/enable-2fa")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> enableTwoFactorAuth() {
        try {
            TwoFactorSetupDto setupDto = accountService.enableTwoFactorAuth();
            return ResponseEntity.ok(setupDto.toMap());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/verify-2fa")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> verifyTwoFactorAuth(@RequestBody TwoFactorVerificationDto verificationDto) {
        try {
            accountService.verifyTwoFactorAuth(verificationDto.getCode());
            return ResponseEntity.ok(Map.of("message", "Two-factor authentication enabled"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/disable-2fa")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> disableTwoFactorAuth(@RequestBody TwoFactorVerificationDto verificationDto) {
        try {
            accountService.disableTwoFactorAuth(verificationDto.getCode());
            return ResponseEntity.ok(Map.of("message", "Two-factor authentication disabled"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Admin endpoints
    
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserAccount>> getAllUsers(Pageable pageable) {
        Page<UserAccount> users = accountService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/admin/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long userId) {
        UserAccount user = accountService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/admin/users/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAccount> updateUserStatus(
            @PathVariable Long userId, 
            @RequestBody UserStatusUpdateDto statusDto) {
        try {
            UserAccount updatedUser = accountService.updateUserStatus(userId, statusDto.getStatus());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PutMapping("/admin/users/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAccount> updateUserRoles(
            @PathVariable Long userId, 
            @RequestBody UserRolesUpdateDto rolesDto) {
        try {
            UserAccount updatedUser = accountService.updateUserRoles(userId, rolesDto.getRoles());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/admin/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long userId) {
        try {
            accountService.deleteUser(userId);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // User management endpoints
    
    @GetMapping("/users/search")
    public ResponseEntity<List<UserAccount>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        
        List<UserAccount> users = accountService.searchUsers(username, email, firstName, lastName);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/users/{userId}/public")
    public ResponseEntity<Map<String, Object>> getPublicUserInfo(@PathVariable Long userId) {
        try {
            Map<String, Object> publicInfo = accountService.getPublicUserInfo(userId);
            return ResponseEntity.ok(publicInfo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Session management
    
    @GetMapping("/sessions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Map<String, Object>>> getUserSessions() {
        List<Map<String, Object>> sessions = accountService.getUserSessions();
        return ResponseEntity.ok(sessions);
    }
    
    @DeleteMapping("/sessions/{sessionId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> terminateSession(@PathVariable String sessionId) {
        try {
            accountService.terminateSession(sessionId);
            return ResponseEntity.ok(Map.of("message", "Session terminated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/sessions/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, String>> terminateAllSessions() {
        try {
            accountService.terminateAllSessions();
            return ResponseEntity.ok(Map.of("message", "All sessions terminated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Health check
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "account-service"));
    }
}
