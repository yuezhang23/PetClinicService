package main.java.com.example.WeePetClinic.event;

import main.java.com.example.WeePetClinic.model.Purchase;
import java.time.LocalDateTime;

public class PurchaseEvent {
    
    private String eventId;
    private String eventType;
    private LocalDateTime timestamp;
    private Purchase purchase;
    private String userId;
    private String source;
    
    public PurchaseEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public PurchaseEvent(String eventType, Purchase purchase, String userId, String source) {
        this();
        this.eventType = eventType;
        this.purchase = purchase;
        this.userId = userId;
        this.source = source;
    }
    
    // Getters and Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Purchase getPurchase() { return purchase; }
    public void setPurchase(Purchase purchase) { this.purchase = purchase; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    // Event types
    public static final String PURCHASE_CREATED = "PURCHASE_CREATED";
    public static final String PURCHASE_COMPLETED = "PURCHASE_COMPLETED";
    public static final String PURCHASE_FAILED = "PURCHASE_FAILED";
    public static final String PURCHASE_CANCELLED = "PURCHASE_CANCELLED";
    public static final String PURCHASE_REFUNDED = "PURCHASE_REFUNDED";
    public static final String PAYMENT_PROCESSED = "PAYMENT_PROCESSED";
    public static final String SUBSCRIPTION_RENEWED = "SUBSCRIPTION_RENEWED";
    public static final String SUBSCRIPTION_EXPIRED = "SUBSCRIPTION_EXPIRED";
}
