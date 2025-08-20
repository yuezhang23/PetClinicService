package main.java.com.example.WeePetClinic.controller;

import main.java.com.example.WeePetClinic.model.Subscription;
import main.java.com.example.WeePetClinic.model.Purchase;
import main.java.com.example.WeePetClinic.service.SubscriptionService;
import main.java.com.example.WeePetClinic.service.PurchaseService;
import main.java.com.example.WeePetClinic.event.SubscriptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private PurchaseService purchaseService;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // Subscription endpoints
    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@Valid @RequestBody Subscription subscription) {
        Subscription created = subscriptionService.createSubscription(subscription);
        
        // Publish event to Kafka
        SubscriptionEvent event = new SubscriptionEvent(
            SubscriptionEvent.SUBSCRIPTION_CREATED,
            created.getUserId(),
            created.getId().toString(),
            created.getSubscriptionType().toString(),
            created.getPrice(),
            created.getStatus().toString()
        );
        kafkaTemplate.send("subscription-events", event);
        
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable Long id) {
        return subscriptionService.getSubscription(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable String userId) {
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, 
                                                        @Valid @RequestBody Subscription subscription) {
        return subscriptionService.updateSubscription(id, subscription)
                .map(updated -> {
                    // Publish event to Kafka
                    SubscriptionEvent event = new SubscriptionEvent(
                        SubscriptionEvent.SUBSCRIPTION_UPDATED,
                        updated.getUserId(),
                        updated.getId().toString(),
                        updated.getSubscriptionType().toString(),
                        updated.getPrice(),
                        updated.getStatus().toString()
                    );
                    kafkaTemplate.send("subscription-events", event);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelSubscription(@PathVariable Long id) {
        if (subscriptionService.cancelSubscription(id)) {
            // Publish event to Kafka
            SubscriptionEvent event = new SubscriptionEvent(
                SubscriptionEvent.SUBSCRIPTION_CANCELLED,
                "system",
                id.toString(),
                "CANCELLED",
                BigDecimal.ZERO,
                "CANCELLED"
            );
            kafkaTemplate.send("subscription-events", event);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Purchase endpoints
    @PostMapping("/purchases")
    public ResponseEntity<Purchase> createPurchase(@Valid @RequestBody Purchase purchase) {
        Purchase created = purchaseService.createPurchase(purchase);
        
        // Publish purchase event to Kafka
        kafkaTemplate.send("purchase-events", created);
        
        return ResponseEntity.ok(created);
    }

    @GetMapping("/purchases/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
        return purchaseService.getPurchase(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/purchases/user/{userId}")
    public ResponseEntity<List<Purchase>> getUserPurchases(@PathVariable String userId) {
        List<Purchase> purchases = purchaseService.getUserPurchases(userId);
        return ResponseEntity.ok(purchases);
    }

    @PutMapping("/purchases/{id}/status")
    public ResponseEntity<Purchase> updatePurchaseStatus(@PathVariable Long id, 
                                                       @RequestParam String status) {
        return purchaseService.updatePurchaseStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Subscription Service is running");
    }
}
