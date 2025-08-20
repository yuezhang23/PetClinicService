package main.java.com.example.WeePetClinic.repository;

import main.java.com.example.WeePetClinic.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findByUserId(String userId);
    
    List<Subscription> findByStatus(Subscription.SubscriptionStatus status);
    
    List<Subscription> findByUserIdAndStatus(String userId, Subscription.SubscriptionStatus status);
    
    @Query("SELECT s FROM Subscription s WHERE s.status = :status AND s.endDate < :endDate")
    List<Subscription> findByStatusAndEndDateBefore(@Param("status") Subscription.SubscriptionStatus status, 
                                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT s FROM Subscription s WHERE s.userId = :userId AND s.status = :status AND s.endDate > :currentDate")
    List<Subscription> findActiveSubscriptionsByUser(@Param("userId") String userId, 
                                                    @Param("status") Subscription.SubscriptionStatus status,
                                                    @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT s FROM Subscription s WHERE s.autoRenew = true AND s.status = :status AND s.endDate BETWEEN :startDate AND :endDate")
    List<Subscription> findSubscriptionsForRenewal(@Param("status") Subscription.SubscriptionStatus status,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);
    
    boolean existsByUserIdAndSubscriptionTypeAndStatus(String userId, 
                                                      Subscription.SubscriptionType subscriptionType, 
                                                      Subscription.SubscriptionStatus status);
}
