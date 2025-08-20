package main.java.com.example.WeePetClinic.repository;

import main.java.com.example.WeePetClinic.model.ClinicLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClinicLocationRepository extends JpaRepository<ClinicLocation, Long> {
    
    List<ClinicLocation> findByCityIgnoreCase(String city);
    
    List<ClinicLocation> findByStateIgnoreCase(String state);
    
    List<ClinicLocation> findByCountryIgnoreCase(String country);
    
    List<ClinicLocation> findByServicesOfferedContainingIgnoreCase(String service);
    
    List<ClinicLocation> findByIsActiveTrue();
    
    List<ClinicLocation> findByRatingGreaterThanEqual(Double minRating);
    
    List<ClinicLocation> findByLatitudeBetweenAndLongitudeBetween(
        BigDecimal minLat, BigDecimal maxLat, 
        BigDecimal minLng, BigDecimal maxLng);
    
    @Query("SELECT c FROM ClinicLocation c WHERE " +
           "c.latitude BETWEEN :minLat AND :maxLat AND " +
           "c.longitude BETWEEN :minLng AND :maxLng AND " +
           "c.isActive = true")
    List<ClinicLocation> findActiveClinicsInBounds(
        @Param("minLat") BigDecimal minLat,
        @Param("maxLat") BigDecimal maxLat,
        @Param("minLng") BigDecimal minLng,
        @Param("maxLng") BigDecimal maxLng);
    
    @Query("SELECT c FROM ClinicLocation c WHERE " +
           "c.city = :city AND c.isActive = true " +
           "ORDER BY c.rating DESC, c.reviewCount DESC")
    List<ClinicLocation> findActiveClinicsByCityOrderByRating(@Param("city") String city);
    
    @Query("SELECT c FROM ClinicLocation c WHERE " +
           "c.servicesOffered LIKE %:service% AND c.isActive = true " +
           "ORDER BY c.rating DESC")
    List<ClinicLocation> findActiveClinicsByServiceOrderByRating(@Param("service") String service);
    
    @Query("SELECT c FROM ClinicLocation c WHERE " +
           "c.isActive = true " +
           "ORDER BY c.rating DESC, c.reviewCount DESC")
    List<ClinicLocation> findTopByOrderByRatingDescReviewCountDesc(int limit);
    
    @Query("SELECT c FROM ClinicLocation c WHERE " +
           "c.isActive = true AND " +
           "c.latitude BETWEEN :minLat AND :maxLat AND " +
           "c.longitude BETWEEN :minLng AND :maxLng " +
           "ORDER BY " +
           "(6371 * acos(cos(radians(:userLat)) * cos(radians(c.latitude)) * " +
           "cos(radians(c.longitude) - radians(:userLng)) + " +
           "sin(radians(:userLat)) * sin(radians(c.latitude))))")
    List<ClinicLocation> findClinicsNearLocationOrderByDistance(
        @Param("userLat") BigDecimal userLat,
        @Param("userLng") BigDecimal userLng,
        @Param("minLat") BigDecimal minLat,
        @Param("maxLat") BigDecimal maxLat,
        @Param("minLng") BigDecimal minLng,
        @Param("maxLng") BigDecimal maxLng);
    
    @Query("SELECT DISTINCT c.city FROM ClinicLocation c WHERE c.isActive = true ORDER BY c.city")
    List<String> findAllActiveCities();
    
    @Query("SELECT DISTINCT c.state FROM ClinicLocation c WHERE c.isActive = true ORDER BY c.state")
    List<String> findAllActiveStates();
    
    @Query("SELECT DISTINCT c.country FROM ClinicLocation c WHERE c.isActive = true ORDER BY c.country")
    List<String> findAllActiveCountries();
    
    @Query("SELECT c FROM ClinicLocation c WHERE " +
           "c.isActive = true AND " +
           "c.rating >= :minRating " +
           "ORDER BY c.rating DESC, c.reviewCount DESC")
    List<ClinicLocation> findTopRatedClinics(@Param("minRating") Double minRating);
    
    @Query("SELECT c FROM ClinicLocation c WHERE " +
           "c.isActive = true AND " +
           "c.reviewCount >= :minReviews " +
           "ORDER BY c.rating DESC")
    List<ClinicLocation> findWellReviewedClinics(@Param("minReviews") Integer minReviews);
}
