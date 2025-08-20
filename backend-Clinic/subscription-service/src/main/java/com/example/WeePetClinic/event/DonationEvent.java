package main.java.com.example.WeePetClinic.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DonationEvent {
    
    private String eventId;
    private String eventType;
    private LocalDateTime timestamp;
    private String donationId;
    private String userId;
    private String donorName;
    private BigDecimal amount;
    private String currency;
    private String cause;
    private String clinicId;
    private String source;
    private String status;
    
    public DonationEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public DonationEvent(String eventType, String donationId, String userId, 
                        String donorName, BigDecimal amount, String cause, String clinicId) {
        this();
        this.eventType = eventType;
        this.donationId = donationId;
        this.userId = userId;
        this.donorName = donorName;
        this.amount = amount;
        this.cause = cause;
        this.clinicId = clinicId;
        this.currency = "USD"; // Default currency
    }
    
    // Getters and Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getDonationId() { return donationId; }
    public void setDonationId(String donationId) { this.donationId = donationId; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getDonorName() { return donorName; }
    public void setDonorName(String donorName) { this.donorName = donorName; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getCause() { return cause; }
    public void setCause(String cause) { this.cause = cause; }
    
    public String getClinicId() { return clinicId; }
    public void setClinicId(String clinicId) { this.clinicId = clinicId; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Event types
    public static final String DONATION_INITIATED = "DONATION_INITIATED";
    public static final String DONATION_COMPLETED = "DONATION_COMPLETED";
    public static final String DONATION_FAILED = "DONATION_FAILED";
    public static final String DONATION_CANCELLED = "DONATION_CANCELLED";
    public static final String DONATION_REFUNDED = "DONATION_REFUNDED";
    public static final String PAYMENT_PROCESSED = "PAYMENT_PROCESSED";
    public static final String RECEIPT_GENERATED = "RECEIPT_GENERATED";
    public static final String TAX_DEDUCTION_AVAILABLE = "TAX_DEDUCTION_AVAILABLE";
}
