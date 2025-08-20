package main.java.com.example.WeePetClinic.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "pet_activities")
public class PetActivity {
    
    @Id
    private String id;
    
    @Indexed
    @NotNull
    private String petId;
    
    @Indexed
    @NotNull
    private String userId;
    
    @NotNull
    private ActivityType activityType;
    
    @TextIndexed
    private String description;
    
    @NotNull
    private LocalDateTime activityDate;
    
    private LocalDateTime endDate;
    
    @Positive
    private Integer duration; // in minutes
    
    private Location location;
    
    private HealthMetrics healthMetrics;
    
    private List<String> tags;
    
    private Map<String, Object> metadata;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // Constructors
    public PetActivity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public PetActivity(String petId, String userId, ActivityType activityType, String description) {
        this();
        this.petId = petId;
        this.userId = userId;
        this.activityType = activityType;
        this.description = description;
        this.activityDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDateTime activityDate) { this.activityDate = activityDate; }
    
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    
    public HealthMetrics getHealthMetrics() { return healthMetrics; }
    public void setHealthMetrics(HealthMetrics healthMetrics) { this.healthMetrics = healthMetrics; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Enums
    public enum ActivityType {
        WALK, PLAY, FEEDING, GROOMING, TRAINING, VET_VISIT, VACCINATION, MEDICATION, REST, EXERCISE
    }
    
    // Inner classes
    public static class Location {
        private Double latitude;
        private Double longitude;
        private String address;
        private String venue;
        
        // Getters and Setters
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        
        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }
        
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public String getVenue() { return venue; }
        public void setVenue(String venue) { this.venue = venue; }
    }
    
    public static class HealthMetrics {
        private Double weight; // in kg
        private Integer heartRate; // bpm
        private Double temperature; // celsius
        private String mood;
        private String appetite;
        private String energyLevel;
        private String notes;
        
        // Getters and Setters
        public Double getWeight() { return weight; }
        public void setWeight(Double weight) { this.weight = weight; }
        
        public Integer getHeartRate() { return heartRate; }
        public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }
        
        public Double getTemperature() { return temperature; }
        public void setTemperature(Double temperature) { this.temperature = temperature; }
        
        public String getMood() { return mood; }
        public void setMood(String mood) { this.mood = mood; }
        
        public String getAppetite() { return appetite; }
        public void setAppetite(String appetite) { this.appetite = appetite; }
        
        public String getEnergyLevel() { return energyLevel; }
        public void setEnergyLevel(String energyLevel) { this.energyLevel = energyLevel; }
        
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }
}
