package main.java.com.example.WeePetClinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "username", unique = true)
    private String username;
    
    @NotNull
    @Email
    @Column(name = "email", unique = true)
    private String email;
    
    @NotNull
    @Size(min = 8, max = 255)
    @Column(name = "password_hash")
    private String passwordHash;
    
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "first_name")
    private String firstName;
    
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "phone")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$")
    private String phone;
    
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;
    
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    
    @Column(name = "bio", length = 1000)
    private String bio;
    
    @Column(name = "preferences", columnDefinition = "TEXT")
    private String preferences; // JSON string for user preferences
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "login_attempts")
    private Integer loginAttempts = 0;
    
    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;
    
    @Column(name = "email_verified")
    private Boolean emailVerified = false;
    
    @Column(name = "phone_verified")
    private Boolean phoneVerified = false;
    
    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled = false;
    
    @Column(name = "two_factor_secret")
    private String twoFactorSecret;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAddress> addresses = new HashSet<>();
    
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserSession> sessions = new HashSet<>();
    
    // Enums
    public enum Gender {
        MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY
    }
    
    public enum AccountType {
        PET_OWNER, VETERINARIAN, CLINIC_STAFF, ADMIN, SYSTEM
    }
    
    public enum AccountStatus {
        ACTIVE, INACTIVE, SUSPENDED, PENDING_VERIFICATION, DELETED
    }
    
    // Constructors
    public UserAccount() {}
    
    public UserAccount(String username, String email, String passwordHash, 
                      String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = AccountStatus.PENDING_VERIFICATION;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Pre-persist and pre-update methods
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = AccountStatus.PENDING_VERIFICATION;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Business logic methods
    public boolean isLocked() {
        return lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now());
    }
    
    public boolean isActive() {
        return AccountStatus.ACTIVE.equals(status) && !isLocked();
    }
    
    public boolean canLogin() {
        return isActive() && emailVerified;
    }
    
    public void incrementLoginAttempts() {
        this.loginAttempts++;
        if (this.loginAttempts >= 5) {
            this.lockedUntil = LocalDateTime.now().plusMinutes(30);
        }
    }
    
    public void resetLoginAttempts() {
        this.loginAttempts = 0;
        this.lockedUntil = null;
    }
    
    public void recordLogin() {
        this.lastLogin = LocalDateTime.now();
        resetLoginAttempts();
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public LocalDateTime getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDateTime dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    
    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }
    
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
    
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    
    public Integer getLoginAttempts() { return loginAttempts; }
    public void setLoginAttempts(Integer loginAttempts) { this.loginAttempts = loginAttempts; }
    
    public LocalDateTime getLockedUntil() { return lockedUntil; }
    public void setLockedUntil(LocalDateTime lockedUntil) { this.lockedUntil = lockedUntil; }
    
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }
    
    public Boolean getPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(Boolean phoneVerified) { this.phoneVerified = phoneVerified; }
    
    public Boolean getTwoFactorEnabled() { return twoFactorEnabled; }
    public void setTwoFactorEnabled(Boolean twoFactorEnabled) { this.twoFactorEnabled = twoFactorEnabled; }
    
    public String getTwoFactorSecret() { return twoFactorSecret; }
    public void setTwoFactorSecret(String twoFactorSecret) { this.twoFactorSecret = twoFactorSecret; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    
    public Set<UserAddress> getAddresses() { return addresses; }
    public void setAddresses(Set<UserAddress> addresses) { this.addresses = addresses; }
    
    public Set<UserRole> getRoles() { return roles; }
    public void setRoles(Set<UserRole> roles) { this.roles = roles; }
    
    public Set<UserSession> getSessions() { return sessions; }
    public void setSessions(Set<UserSession> sessions) { this.sessions = sessions; }
}
