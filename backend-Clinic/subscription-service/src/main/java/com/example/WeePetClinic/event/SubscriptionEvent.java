package main.java.com.example.WeePetClinic.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SubscriptionEvent {
    
    private String eventId;
    private String eventType;
    private String userId;
    private String subscriptionId;
    private String subscriptionType;
    private BigDecimal amount;
    private String status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    private String correlationId;
    
    // Constructors
    public SubscriptionEvent() {}
    
    public SubscriptionEvent(String eventType, String userId, String subscriptionId, 
                           String subscriptionType, BigDecimal amount, String status) {
        this.eventId = java.util.UUID.randomUUID().toString();
        this.eventType = eventType;
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.subscriptionType = subscriptionType;
        this.amount = amount;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.correlationId = java.util.UUID.randomUUID().toString();
    }
    
    // Getters and Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
    
    public String getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(String subscriptionType) { this.subscriptionType = subscriptionType; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
    
    // Event types
    public static final String SUBSCRIPTION_CREATED = "SUBSCRIPTION_CREATED";
    public static final String SUBSCRIPTION_UPDATED = "SUBSCRIPTION_UPDATED";
    public static final String SUBSCRIPTION_CANCELLED = "SUBSCRIPTION_CANCELLED";
    public static final String SUBSCRIPTION_EXPIRED = "SUBSCRIPTION_EXPIRED";
    public static final String SUBSCRIPTION_RENEWED = "SUBSCRIPTION_RENEWED";
}
