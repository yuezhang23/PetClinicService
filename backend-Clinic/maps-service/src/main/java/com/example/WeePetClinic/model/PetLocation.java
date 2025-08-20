package main.java.com.example.WeePetClinic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pet_locations")
public class PetLocation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(name = "pet_id")
    private String petId;
    
    @NotNull
    @Column(name = "user_id")
    private String userId;
    
    @NotNull
    @Column(name = "activity_type")
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    
    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;
    
    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "venue")
    private String venue;
    
    @Column(name = "accuracy")
    private Double accuracy; // GPS accuracy in meters
    
    @Column(name = "altitude")
    private Double altitude; // Altitude in meters
    
    @Column(name = "speed")
    private Double speed; // Speed in m/s
    
    @Column(name = "heading")
    private Double heading; // Direction in degrees
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "duration")
    private Integer duration; // Duration in minutes
    
    @Column(name = "distance")
    private Double distance; // Distance traveled in meters
    
    @Column(name = "calories_burned")
    private Integer caloriesBurned;
    
    @Column(name = "weather_conditions")
    private String weatherConditions;
    
    @Column(name = "temperature")
    private Double temperature; // in Celsius
    
    @Column(name = "humidity")
    private Double humidity; // percentage
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public PetLocation() {}
    
    public PetLocation(String petId, String userId, ActivityType activityType, 
                      BigDecimal latitude, BigDecimal longitude) {
        this.petId = petId;
        this.userId = userId;
        this.activityType = activityType;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    // Enums
    public enum ActivityType {
        WALK, RUN, PLAY, TRAINING, GROOMING, VET_VISIT, REST, FEEDING, OTHER
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    
    public Double getAccuracy() { return accuracy; }
    public void setAccuracy(Double accuracy) { this.accuracy = accuracy; }
    
    public Double getAltitude() { return altitude; }
    public void setAltitude(Double altitude) { this.altitude = altitude; }
    
    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }
    
    public Double getHeading() { return heading; }
    public void setHeading(Double heading) { this.heading = heading; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    
    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }
    
    public Integer getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Integer caloriesBurned) { this.caloriesBurned = caloriesBurned; }
    
    public String getWeatherConditions() { return weatherConditions; }
    public void setWeatherConditions(String weatherConditions) { this.weatherConditions = weatherConditions; }
    
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
