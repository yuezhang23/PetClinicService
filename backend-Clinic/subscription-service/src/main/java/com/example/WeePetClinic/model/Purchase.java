package main.java.com.example.WeePetClinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(name = "user_id")
    private String userId;
    
    @NotNull
    @Column(name = "purchase_type")
    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;
    
    @NotNull
    @Column(name = "amount")
    @Positive
    private BigDecimal amount;
    
    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "reference_id")
    private String referenceId; // For linking to appointments, services, etc.
    
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    @Column(name = "transaction_id")
    private String transactionId;
    
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (purchaseDate == null) {
            purchaseDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Purchase() {}
    
    public Purchase(String userId, PurchaseType purchaseType, BigDecimal amount, String description) {
        this.userId = userId;
        this.purchaseType = purchaseType;
        this.amount = amount;
        this.description = description;
        this.status = PurchaseStatus.PENDING;
        this.purchaseDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public PurchaseType getPurchaseType() { return purchaseType; }
    public void setPurchaseType(PurchaseType purchaseType) { this.purchaseType = purchaseType; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public PurchaseStatus getStatus() { return status; }
    public void setStatus(PurchaseStatus status) { this.status = status; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
    
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public enum PurchaseType {
        APPOINTMENT, MEDICATION, TREATMENT, CONSULTATION, EMERGENCY, SUBSCRIPTION
    }
    
    public enum PurchaseStatus {
        PENDING, COMPLETED, FAILED, CANCELLED, REFUNDED
    }
    
    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, CASH, DIGITAL_WALLET
    }
}
