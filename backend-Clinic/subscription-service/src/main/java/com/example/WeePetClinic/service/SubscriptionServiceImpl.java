package main.java.com.example.WeePetClinic.service;

import main.java.com.example.WeePetClinic.model.Subscription;
import main.java.com.example.WeePetClinic.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(Subscription subscription) {
        // Set default values
        if (subscription.getStartDate() == null) {
            subscription.setStartDate(LocalDateTime.now());
        }
        if (subscription.getStatus() == null) {
            subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        }
        
        // Calculate end date based on subscription type
        LocalDateTime endDate = calculateEndDate(subscription.getStartDate(), subscription.getSubscriptionType());
        subscription.setEndDate(endDate);
        
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Optional<Subscription> getSubscription(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public List<Subscription> getUserSubscriptions(String userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public Optional<Subscription> updateSubscription(Long id, Subscription subscriptionDetails) {
        return subscriptionRepository.findById(id)
                .map(existingSubscription -> {
                    // Update fields
                    if (subscriptionDetails.getSubscriptionType() != null) {
                        existingSubscription.setSubscriptionType(subscriptionDetails.getSubscriptionType());
                    }
                    if (subscriptionDetails.getPrice() != null) {
                        existingSubscription.setPrice(subscriptionDetails.getPrice());
                    }
                    if (subscriptionDetails.getStatus() != null) {
                        existingSubscription.setStatus(subscriptionDetails.getStatus());
                    }
                    if (subscriptionDetails.getAutoRenew() != existingSubscription.isAutoRenew()) {
                        existingSubscription.setAutoRenew(subscriptionDetails.isAutoRenew());
                    }
                    
                    // Recalculate end date if subscription type changed
                    if (subscriptionDetails.getSubscriptionType() != null && 
                        !subscriptionDetails.getSubscriptionType().equals(existingSubscription.getSubscriptionType())) {
                        LocalDateTime newEndDate = calculateEndDate(existingSubscription.getStartDate(), 
                                                                 subscriptionDetails.getSubscriptionType());
                        existingSubscription.setEndDate(newEndDate);
                    }
                    
                    return subscriptionRepository.save(existingSubscription);
                });
    }

    @Override
    public boolean cancelSubscription(Long id) {
        return subscriptionRepository.findById(id)
                .map(subscription -> {
                    subscription.setStatus(Subscription.SubscriptionStatus.CANCELLED);
                    subscription.setAutoRenew(false);
                    subscriptionRepository.save(subscription);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findByStatus(Subscription.SubscriptionStatus.ACTIVE);
    }

    @Override
    public List<Subscription> getExpiredSubscriptions() {
        return subscriptionRepository.findByStatusAndEndDateBefore(
            Subscription.SubscriptionStatus.ACTIVE, LocalDateTime.now());
    }

    @Override
    public boolean renewSubscription(Long id) {
        return subscriptionRepository.findById(id)
                .map(subscription -> {
                    if (subscription.getStatus() == Subscription.SubscriptionStatus.EXPIRED) {
                        LocalDateTime newStartDate = LocalDateTime.now();
                        LocalDateTime newEndDate = calculateEndDate(newStartDate, subscription.getSubscriptionType());
                        
                        subscription.setStartDate(newStartDate);
                        subscription.setEndDate(newEndDate);
                        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
                        subscription.setAutoRenew(true);
                        
                        subscriptionRepository.save(subscription);
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }

    @Override
    public boolean isSubscriptionActive(String userId, String subscriptionType) {
        List<Subscription> activeSubscriptions = subscriptionRepository.findByUserIdAndStatus(
            userId, Subscription.SubscriptionStatus.ACTIVE);
        
        return activeSubscriptions.stream()
                .anyMatch(sub -> sub.getSubscriptionType().toString().equals(subscriptionType) &&
                               sub.getEndDate().isAfter(LocalDateTime.now()));
    }

    private LocalDateTime calculateEndDate(LocalDateTime startDate, Subscription.SubscriptionType type) {
        switch (type) {
            case BASIC:
                return startDate.plusMonths(1);
            case PREMIUM:
                return startDate.plusMonths(3);
            case VIP:
                return startDate.plusMonths(6);
            case EMERGENCY:
                return startDate.plusDays(7);
            default:
                return startDate.plusMonths(1);
        }
    }
}
