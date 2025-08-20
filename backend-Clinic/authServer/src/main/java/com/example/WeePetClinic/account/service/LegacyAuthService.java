package com.example.WeePetClinic.account.service;

import com.example.WeePetClinic.Components.Service.ServiceUserLogin;
import com.example.WeePetClinic.utilities.TypeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LegacyAuthService {
    
    @Autowired
    private ServiceUserLogin serviceUserLogin;
    
    /**
     * Legacy authentication using existing service
     */
    public Map<TypeUser, String> signInRoles(String username, String password) {
        try {
            return serviceUserLogin.signInRoles(username, password);
        } catch (Exception e) {
            System.err.println("Legacy authentication error: " + e.getMessage());
            return new HashMap<>();
        }
    }
    
    /**
     * Get user information from legacy system
     */
    public Map<String, Object> getUserInfo(String username) {
        try {
            // This is a simplified implementation
            // In a real system, you would query the legacy database
            return Map.of(
                "username", username,
                "authMethod", "legacy",
                "message", "Legacy user information not fully implemented"
            );
        } catch (Exception e) {
            return Map.of("error", "Failed to get legacy user info");
        }
    }
}
