package main.java.com.example.WeePetClinic.service;

import main.java.com.example.WeePetClinic.model.Subscription;
import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    
    Subscription createSubscription(Subscription subscription);
    
    Optional<Subscription> getSubscription(Long id);
    
    List<Subscription> getUserSubscriptions(String userId);
    
    Optional<Subscription> updateSubscription(Long id, Subscription subscription);
    
    boolean cancelSubscription(Long id);
    
    List<Subscription> getActiveSubscriptions();
    
    List<Subscription> getExpiredSubscriptions();
    
    boolean renewSubscription(Long id);
    
    boolean isSubscriptionActive(String userId, String subscriptionType);
}
