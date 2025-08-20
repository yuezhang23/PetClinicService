package main.java.com.example.WeePetClinic.service;

import main.java.com.example.WeePetClinic.model.ClinicLocation;
import main.java.com.example.WeePetClinic.model.PetLocation;
import main.java.com.example.WeePetClinic.repository.ClinicLocationRepository;
import main.java.com.example.WeePetClinic.repository.PetLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapsService {
    
    @Autowired
    private ClinicLocationRepository clinicLocationRepository;
    
    @Autowired
    private PetLocationRepository petLocationRepository;
    
    // Clinic Location Services
    
    @Cacheable(value = "clinics", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<ClinicLocation> getAllClinics(Pageable pageable) {
        return clinicLocationRepository.findAll(pageable);
    }
    
    @Cacheable(value = "clinics", key = "#id")
    public ClinicLocation getClinicById(Long id) {
        return clinicLocationRepository.findById(id).orElse(null);
    }
    
    public List<ClinicLocation> findClinicsNearLocation(BigDecimal latitude, BigDecimal longitude, Double radiusKm) {
        // Convert radius from km to degrees (approximate)
        double radiusDegrees = radiusKm / 111.0; // 1 degree ≈ 111 km
        
        BigDecimal minLat = latitude.subtract(BigDecimal.valueOf(radiusDegrees));
        BigDecimal maxLat = latitude.add(BigDecimal.valueOf(radiusDegrees));
        BigDecimal minLng = longitude.subtract(BigDecimal.valueOf(radiusDegrees));
        BigDecimal maxLng = longitude.add(BigDecimal.valueOf(radiusDegrees));
        
        List<ClinicLocation> clinics = clinicLocationRepository.findByLatitudeBetweenAndLongitudeBetween(
            minLat, maxLat, minLng, maxLng);
        
        // Filter by actual distance using Haversine formula
        return clinics.stream()
            .filter(clinic -> calculateDistance(latitude, longitude, clinic.getLatitude(), clinic.getLongitude()) <= radiusKm)
            .sorted(Comparator.comparing(clinic -> 
                calculateDistance(latitude, longitude, clinic.getLatitude(), clinic.getLongitude())))
            .collect(Collectors.toList());
    }
    
    @Cacheable(value = "clinics", key = "'city_' + #city")
    public List<ClinicLocation> getClinicsByCity(String city) {
        return clinicLocationRepository.findByCityIgnoreCase(city);
    }
    
    @Cacheable(value = "clinics", key = "'service_' + #service")
    public List<ClinicLocation> getClinicsByService(String service) {
        return clinicLocationRepository.findByServicesOfferedContainingIgnoreCase(service);
    }
    
    @CacheEvict(value = "clinics", allEntries = true)
    public ClinicLocation createClinic(ClinicLocation clinic) {
        clinic.setCreatedAt(LocalDateTime.now());
        clinic.setUpdatedAt(LocalDateTime.now());
        return clinicLocationRepository.save(clinic);
    }
    
    @CachePut(value = "clinics", key = "#id")
    public ClinicLocation updateClinic(Long id, ClinicLocation clinic) {
        Optional<ClinicLocation> existingClinic = clinicLocationRepository.findById(id);
        if (existingClinic.isPresent()) {
            ClinicLocation updatedClinic = existingClinic.get();
            updatedClinic.setClinicName(clinic.getClinicName());
            updatedClinic.setAddress(clinic.getAddress());
            updatedClinic.setCity(clinic.getCity());
            updatedClinic.setState(clinic.getState());
            updatedClinic.setZipCode(clinic.getZipCode());
            updatedClinic.setCountry(clinic.getCountry());
            updatedClinic.setLatitude(clinic.getLatitude());
            updatedClinic.setLongitude(clinic.getLongitude());
            updatedClinic.setPhone(clinic.getPhone());
            updatedClinic.setEmail(clinic.getEmail());
            updatedClinic.setWebsite(clinic.getWebsite());
            updatedClinic.setOperatingHours(clinic.getOperatingHours());
            updatedClinic.setServicesOffered(clinic.getServicesOffered());
            updatedClinic.setRating(clinic.getRating());
            updatedClinic.setReviewCount(clinic.getReviewCount());
            updatedClinic.setIsActive(clinic.getIsActive());
            updatedClinic.setUpdatedAt(LocalDateTime.now());
            
            return clinicLocationRepository.save(updatedClinic);
        }
        return null;
    }
    
    @CacheEvict(value = "clinics", key = "#id")
    public boolean deleteClinic(Long id) {
        if (clinicLocationRepository.existsById(id)) {
            clinicLocationRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Pet Location Services
    
    public Page<PetLocation> getPetLocations(String petId, Pageable pageable) {
        return petLocationRepository.findByPetIdOrderByTimestampDesc(petId, pageable);
    }
    
    public List<PetLocation> getRecentPetLocations(String petId, int limit) {
        return petLocationRepository.findTopByPetIdOrderByTimestampDesc(petId, limit);
    }
    
    public Map<String, Object> getPetActivitySummary(String petId, int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<PetLocation> locations = petLocationRepository.findByPetIdAndTimestampAfter(petId, startDate);
        
        Map<String, Object> summary = new HashMap<>();
        summary.put("petId", petId);
        summary.put("periodDays", days);
        summary.put("totalActivities", locations.size());
        
        // Calculate total distance
        double totalDistance = locations.stream()
            .mapToDouble(loc -> loc.getDistance() != null ? loc.getDistance() : 0.0)
            .sum();
        summary.put("totalDistanceKm", Math.round(totalDistance / 1000.0 * 100.0) / 100.0);
        
        // Calculate total duration
        int totalDuration = locations.stream()
            .mapToInt(loc -> loc.getDuration() != null ? loc.getDuration() : 0)
            .sum();
        summary.put("totalDurationHours", Math.round(totalDuration / 60.0 * 100.0) / 100.0);
        
        // Calculate total calories
        int totalCalories = locations.stream()
            .mapToInt(loc -> loc.getCaloriesBurned() != null ? loc.getCaloriesBurned() : 0)
            .sum();
        summary.put("totalCaloriesBurned", totalCalories);
        
        // Activity type breakdown
        Map<PetLocation.ActivityType, Long> activityBreakdown = locations.stream()
            .collect(Collectors.groupingBy(PetLocation::getActivityType, Collectors.counting()));
        summary.put("activityBreakdown", activityBreakdown);
        
        // Most visited locations
        Map<String, Long> locationFrequency = locations.stream()
            .collect(Collectors.groupingBy(
                loc -> loc.getLatitude() + "," + loc.getLongitude(),
                Collectors.counting()));
        
        String mostVisitedLocation = locationFrequency.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("None");
        summary.put("mostVisitedLocation", mostVisitedLocation);
        
        return summary;
    }
    
    public PetLocation recordPetLocation(PetLocation location) {
        location.setCreatedAt(LocalDateTime.now());
        location.setUpdatedAt(LocalDateTime.now());
        if (location.getTimestamp() == null) {
            location.setTimestamp(LocalDateTime.now());
        }
        return petLocationRepository.save(location);
    }
    
    public List<PetLocation> recordPetLocations(List<PetLocation> locations) {
        locations.forEach(location -> {
            location.setCreatedAt(LocalDateTime.now());
            location.setUpdatedAt(LocalDateTime.now());
            if (location.getTimestamp() == null) {
                location.setTimestamp(LocalDateTime.now());
            }
        });
        return petLocationRepository.saveAll(locations);
    }
    
    // Route Planning Services
    
    public Map<String, Object> getRouteBetweenClinics(Long fromClinicId, Long toClinicId, String mode) {
        ClinicLocation fromClinic = getClinicById(fromClinicId);
        ClinicLocation toClinic = getClinicById(toClinicId);
        
        if (fromClinic == null || toClinic == null) {
            return Map.of("error", "One or both clinics not found");
        }
        
        Map<String, Object> route = new HashMap<>();
        route.put("from", fromClinic);
        route.put("to", toClinic);
        route.put("mode", mode);
        route.put("distance", calculateDistance(
            fromClinic.getLatitude(), fromClinic.getLongitude(),
            toClinic.getLatitude(), toClinic.getLongitude()));
        route.put("estimatedTime", estimateTravelTime(mode, 
            calculateDistance(fromClinic.getLatitude(), fromClinic.getLongitude(),
                           toClinic.getLatitude(), toClinic.getLongitude())));
        
        return route;
    }
    
    public Map<String, Object> getRouteToClinic(BigDecimal userLat, BigDecimal userLng, Long clinicId, String mode) {
        ClinicLocation clinic = getClinicById(clinicId);
        
        if (clinic == null) {
            return Map.of("error", "Clinic not found");
        }
        
        Map<String, Object> route = new HashMap<>();
        route.put("clinic", clinic);
        route.put("userLocation", Map.of("latitude", userLat, "longitude", userLng));
        route.put("mode", mode);
        route.put("distance", calculateDistance(userLat, userLng, clinic.getLatitude(), clinic.getLongitude()));
        route.put("estimatedTime", estimateTravelTime(mode, 
            calculateDistance(userLat, userLng, clinic.getLatitude(), clinic.getLongitude())));
        
        return route;
    }
    
    // Analytics Services
    
    @Cacheable(value = "analytics", key = "'popular_clinics_' + #limit")
    public List<Map<String, Object>> getPopularClinics(int limit) {
        List<ClinicLocation> clinics = clinicLocationRepository.findTopByOrderByRatingDescReviewCountDesc(limit);
        
        return clinics.stream()
            .map(clinic -> {
                Map<String, Object> clinicData = new HashMap<>();
                clinicData.put("id", clinic.getId());
                clinicData.put("name", clinic.getClinicName());
                clinicData.put("rating", clinic.getRating());
                clinicData.put("reviewCount", clinic.getReviewCount());
                clinicData.put("city", clinic.getCity());
                clinicData.put("state", clinic.getState());
                return clinicData;
            })
            .collect(Collectors.toList());
    }
    
    public Map<String, Object> getPetActivityHeatmap(String petId, int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<PetLocation> locations = petLocationRepository.findByPetIdAndTimestampAfter(petId, startDate);
        
        Map<String, Object> heatmap = new HashMap<>();
        heatmap.put("petId", petId);
        heatmap.put("periodDays", days);
        heatmap.put("totalPoints", locations.size());
        
        // Group locations by day for heatmap visualization
        Map<String, Long> dailyActivity = locations.stream()
            .collect(Collectors.groupingBy(
                loc -> loc.getTimestamp().toLocalDate().toString(),
                Collectors.counting()));
        heatmap.put("dailyActivity", dailyActivity);
        
        // Activity intensity by location
        Map<String, Integer> locationIntensity = new HashMap<>();
        for (PetLocation location : locations) {
            String key = location.getLatitude() + "," + location.getLongitude();
            locationIntensity.merge(key, 1, Integer::sum);
        }
        heatmap.put("locationIntensity", locationIntensity);
        
        return heatmap;
    }
    
    // Utility Methods
    
    private double calculateDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {
        // Haversine formula for calculating distance between two points
        double lat1Rad = Math.toRadians(lat1.doubleValue());
        double lat2Rad = Math.toRadians(lat2.doubleValue());
        double deltaLat = Math.toRadians(lat2.doubleValue() - lat1.doubleValue());
        double deltaLng = Math.toRadians(lng2.doubleValue() - lng1.doubleValue());
        
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return 6371 * c; // Earth's radius in km
    }
    
    private String estimateTravelTime(String mode, double distanceKm) {
        double speedKmh;
        switch (mode.toLowerCase()) {
            case "walking":
                speedKmh = 5.0;
                break;
            case "cycling":
                speedKmh = 20.0;
                break;
            case "transit":
                speedKmh = 30.0;
                break;
            case "driving":
            default:
                speedKmh = 50.0;
                break;
        }
        
        double timeHours = distanceKm / speedKmh;
        int minutes = (int) Math.round(timeHours * 60);
        
        if (minutes < 60) {
            return minutes + " minutes";
        } else {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            return hours + "h " + remainingMinutes + "m";
        }
    }
}
