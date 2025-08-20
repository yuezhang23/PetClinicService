package main.java.com.example.WeePetClinic.repository;

import main.java.com.example.WeePetClinic.model.PetLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PetLocationRepository extends JpaRepository<PetLocation, Long> {
    
    Page<PetLocation> findByPetIdOrderByTimestampDesc(String petId, Pageable pageable);
    
    List<PetLocation> findByPetIdOrderByTimestampDesc(String petId);
    
    List<PetLocation> findTopByPetIdOrderByTimestampDesc(String petId, int limit);
    
    List<PetLocation> findByUserIdOrderByTimestampDesc(String userId);
    
    List<PetLocation> findByPetIdAndTimestampAfter(String petId, LocalDateTime timestamp);
    
    List<PetLocation> findByUserIdAndTimestampAfter(String userId, LocalDateTime timestamp);
    
    List<PetLocation> findByPetIdAndActivityType(String petId, PetLocation.ActivityType activityType);
    
    List<PetLocation> findByPetIdAndTimestampBetween(String petId, LocalDateTime startTime, LocalDateTime endTime);
    
    List<PetLocation> findByUserIdAndTimestampBetween(String userId, LocalDateTime startTime, LocalDateTime endTime);
    
    List<PetLocation> findByActivityTypeAndTimestampAfter(PetLocation.ActivityType activityType, LocalDateTime timestamp);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.timestamp >= :startDate AND " +
           "p.timestamp <= :endDate " +
           "ORDER BY p.timestamp DESC")
    List<PetLocation> findPetLocationsInDateRange(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.userId = :userId AND " +
           "p.timestamp >= :startDate AND " +
           "p.timestamp <= :endDate " +
           "ORDER BY p.timestamp DESC")
    List<PetLocation> findUserLocationsInDateRange(
        @Param("userId") String userId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.latitude BETWEEN :minLat AND :maxLat AND " +
           "p.longitude BETWEEN :minLng AND :maxLng " +
           "ORDER BY p.timestamp DESC")
    List<PetLocation> findPetLocationsInBounds(
        @Param("petId") String petId,
        @Param("minLat") BigDecimal minLat,
        @Param("maxLat") BigDecimal maxLat,
        @Param("minLng") BigDecimal minLng,
        @Param("maxLng") BigDecimal maxLng);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.userId = :userId AND " +
           "p.latitude BETWEEN :minLat AND :maxLat AND " +
           "p.longitude BETWEEN :minLng AND :maxLng " +
           "ORDER BY p.timestamp DESC")
    List<PetLocation> findUserLocationsInBounds(
        @Param("userId") String userId,
        @Param("minLat") BigDecimal minLat,
        @Param("maxLat") BigDecimal maxLat,
        @Param("minLng") BigDecimal minLng,
        @Param("maxLng") BigDecimal maxLng);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.activityType = :activityType AND " +
           "p.timestamp >= :startDate " +
           "ORDER BY p.timestamp DESC")
    List<PetLocation> findPetActivitiesByType(
        @Param("petId") String petId,
        @Param("activityType") PetLocation.ActivityType activityType,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.distance IS NOT NULL AND " +
           "p.timestamp >= :startDate " +
           "ORDER BY p.distance DESC")
    List<PetLocation> findPetActivitiesByDistance(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.duration IS NOT NULL AND " +
           "p.timestamp >= :startDate " +
           "ORDER BY p.duration DESC")
    List<PetLocation> findPetActivitiesByDuration(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.caloriesBurned IS NOT NULL AND " +
           "p.timestamp >= :startDate " +
           "ORDER BY p.caloriesBurned DESC")
    List<PetLocation> findPetActivitiesByCalories(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.weatherConditions IS NOT NULL AND " +
           "p.timestamp >= :startDate " +
           "ORDER BY p.timestamp DESC")
    List<PetLocation> findPetActivitiesWithWeather(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.venue IS NOT NULL AND " +
           "p.venue != '' " +
           "ORDER BY p.timestamp DESC")
    List<PetLocation> findPetActivitiesByVenue(@Param("petId") String petId);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.timestamp >= :startDate " +
           "GROUP BY DATE(p.timestamp) " +
           "ORDER BY DATE(p.timestamp) DESC")
    List<Object[]> findPetDailyActivitySummary(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.timestamp >= :startDate " +
           "GROUP BY p.activityType " +
           "ORDER BY COUNT(p) DESC")
    List<Object[]> findPetActivityTypeSummary(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT p FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.timestamp >= :startDate " +
           "ORDER BY p.timestamp DESC " +
           "LIMIT 1")
    PetLocation findLatestPetLocation(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT COUNT(p) FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.timestamp >= :startDate")
    Long countPetActivitiesInPeriod(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT AVG(p.distance) FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.distance IS NOT NULL AND " +
           "p.timestamp >= :startDate")
    Double getAveragePetActivityDistance(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT AVG(p.duration) FROM PetLocation p WHERE " +
           "p.petId = :petId AND " +
           "p.duration IS NOT NULL AND " +
           "p.timestamp >= :startDate")
    Double getAveragePetActivityDuration(
        @Param("petId") String petId,
        @Param("startDate") LocalDateTime startDate);
}
