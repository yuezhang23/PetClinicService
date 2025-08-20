package com.example.WeePetClinic.account.service;

import com.example.WeePetClinic.account.model.UserAccount;
import com.example.WeePetClinic.utilities.TypeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UnifiedAuthService {
    
    @Autowired
    private UserAccountService userAccountService;
    
    @Autowired
    private LegacyAuthService legacyAuthService;
    
    @Autowired
    private JwtService jwtService;
    
    /**
     * Unified authentication that tries both new account service and legacy authentication
     */
    public Map<String, Object> authenticateUser(String username, String password) {
        // First try new account service authentication
        try {
            Optional<UserAccount> userOpt = userAccountService.findByUsername(username);
            if (userOpt.isPresent()) {
                UserAccount user = userOpt.get();
                if (userAccountService.validatePassword(user, password)) {
                    return createNewAuthResponse(user);
                }
            }
        } catch (Exception e) {
            // Log error but continue to legacy auth
            System.err.println("New auth failed: " + e.getMessage());
        }
        
        // If new auth fails, try legacy authentication
        try {
            Map<TypeUser, String> legacyRoles = legacyAuthService.signInRoles(username, password);
            if (!legacyRoles.isEmpty()) {
                return createLegacyAuthResponse(username, legacyRoles);
            }
        } catch (Exception e) {
            System.err.println("Legacy auth failed: " + e.getMessage());
        }
        
        // Both authentication methods failed
        return Map.of("authenticated", false, "error", "Invalid credentials");
    }
    
    /**
     * Create authentication response for new account service users
     */
    private Map<String, Object> createNewAuthResponse(UserAccount user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        
        // Record successful login
        user.recordLogin();
        userAccountService.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", true);
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("tokenType", "Bearer");
        response.put("expiresIn", 3600L);
        response.put("username", user.getUsername());
        response.put("userType", user.getUserType().name());
        response.put("userId", user.getId().toString());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("email", user.getEmail());
        response.put("authMethod", "new");
        
        return response;
    }
    
    /**
     * Create authentication response for legacy users
     */
    private Map<String, Object> createLegacyAuthResponse(String username, Map<TypeUser, String> roles) {
        // For legacy users, create a simple token and return role information
        String legacyToken = "legacy_" + System.currentTimeMillis();
        
        return Map.of(
            "authenticated", true,
            "accessToken", legacyToken,
            "refreshToken", "legacy_refresh_" + System.currentTimeMillis(),
            "tokenType", "Legacy",
            "expiresIn", 3600L,
            "username", username,
            "roles", roles,
            "authMethod", "legacy",
            "message", "Legacy authentication successful"
        );
    }
    
    /**
     * Refresh token for new account service users
     */
    public Map<String, Object> refreshToken(String refreshToken) {
        try {
            if (refreshToken.startsWith("legacy_")) {
                // Legacy tokens can't be refreshed, return error
                return Map.of("error", "Legacy tokens cannot be refreshed");
            }
            
            String username = jwtService.extractUsername(refreshToken);
            Optional<UserAccount> userOpt = userAccountService.findByUsername(username);
            
            if (userOpt.isPresent()) {
                UserAccount user = userOpt.get();
                String newAccessToken = jwtService.generateAccessToken(user);
                
                return Map.of(
                    "accessToken", newAccessToken,
                    "refreshToken", refreshToken,
                    "expiresIn", 3600L,
                    "username", username
                );
            }
        } catch (Exception e) {
            return Map.of("error", "Invalid refresh token");
        }
        
        return Map.of("error", "User not found");
    }
    
    /**
     * Register new user
     */
    public Map<String, Object> registerUser(Map<String, Object> registrationRequest) {
        try {
            String username = (String) registrationRequest.get("username");
            String email = (String) registrationRequest.get("email");
            String password = (String) registrationRequest.get("password");
            String firstName = (String) registrationRequest.get("firstName");
            String lastName = (String) registrationRequest.get("lastName");
            
            // Check if user already exists
            if (userAccountService.findByUsername(username).isPresent()) {
                return Map.of("error", "Username already exists");
            }
            
            if (userAccountService.findByEmail(email).isPresent()) {
                return Map.of("error", "Email already exists");
            }
            
            // Create new user
            UserAccount newUser = userAccountService.createUser(username, email, password, firstName, lastName);
            
            return Map.of(
                "success", true,
                "message", "User registered successfully",
                "userId", newUser.getId().toString()
            );
            
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
    
    /**
     * Get user information
     */
    public Map<String, Object> getUserInfo(String username) {
        try {
            Optional<UserAccount> userOpt = userAccountService.findByUsername(username);
            if (userOpt.isPresent()) {
                UserAccount user = userOpt.get();
                return Map.of(
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName(),
                    "userType", user.getUserType().name(),
                    "status", user.getStatus().name(),
                    "lastLogin", user.getLastLogin()
                );
            }
        } catch (Exception e) {
            // Try legacy user info
            try {
                return legacyAuthService.getUserInfo(username);
            } catch (Exception le) {
                return Map.of("error", "User not found");
            }
        }
        
        return Map.of("error", "User not found");
    }
    
    /**
     * Logout user
     */
    public void logoutUser(String username) {
        // In a real implementation, you might want to:
        // 1. Invalidate JWT tokens
        // 2. Log the logout event
        // 3. Clear any session data
        
        System.out.println("User logged out: " + username);
    }
}
