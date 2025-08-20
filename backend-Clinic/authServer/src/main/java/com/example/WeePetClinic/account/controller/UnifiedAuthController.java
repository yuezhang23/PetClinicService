package com.example.WeePetClinic.account.controller;

import com.example.WeePetClinic.account.dto.LoginRequestDto;
import com.example.WeePetClinic.account.service.UnifiedAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UnifiedAuthController {
    
    @Autowired
    private UnifiedAuthService unifiedAuthService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody LoginRequestDto loginDto) {
        try {
            // Try unified authentication (combines both approaches)
            Map<String, Object> authResult = unifiedAuthService.authenticateUser(loginDto.getUsername(), loginDto.getPassword());
            
            if ((Boolean) authResult.get("authenticated")) {
                return ResponseEntity.ok(authResult);
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody Map<String, String> refreshRequest) {
        try {
            String refreshToken = refreshRequest.get("refreshToken");
            Map<String, Object> newTokens = unifiedAuthService.refreshToken(refreshToken);
            return ResponseEntity.ok(newTokens);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody Map<String, Object> registrationRequest) {
        try {
            Map<String, Object> result = unifiedAuthService.registerUser(registrationRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/user/{username}")
    public ResponseEntity<Map<String, Object>> getUserInfo(@PathVariable String username) {
        try {
            Map<String, Object> userInfo = unifiedAuthService.getUserInfo(username);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logoutUser(@RequestBody Map<String, String> logoutRequest) {
        try {
            String username = logoutRequest.get("username");
            unifiedAuthService.logoutUser(username);
            return ResponseEntity.ok(Map.of("message", "User logged out successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
