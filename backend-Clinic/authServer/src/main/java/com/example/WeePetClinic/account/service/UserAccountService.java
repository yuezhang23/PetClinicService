package com.example.WeePetClinic.account.service;

import com.example.WeePetClinic.account.model.UserAccount;
import com.example.WeePetClinic.account.repository.UserAccountRepository;
import com.example.WeePetClinic.utilities.TypeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {
    
    @Autowired
    private UserAccountRepository userAccountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }
    
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }
    
    public boolean validatePassword(UserAccount user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }
    
    public UserAccount save(UserAccount user) {
        return userAccountRepository.save(user);
    }
    
    public UserAccount createUser(String username, String email, String password, String firstName, String lastName) {
        UserAccount user = new UserAccount(
            username, 
            email, 
            passwordEncoder.encode(password), 
            firstName, 
            lastName, 
            TypeUser.pet_owner // Default role
        );
        return userAccountRepository.save(user);
    }
}
