package main.java.com.example.WeePetClinic.controller;

import main.java.com.example.WeePetClinic.model.PetActivity;
import main.java.com.example.WeePetClinic.service.PetActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:3000")
public class PetActivityController {

    @Autowired
    private PetActivityService petActivityService;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // Create new pet activity
    @PostMapping
    public ResponseEntity<PetActivity> createActivity(@Valid @RequestBody PetActivity activity) {
        PetActivity created = petActivityService.createActivity(activity);
        
        // Publish activity event to Kafka
        kafkaTemplate.send("pet-activity-events", created);
        
        return ResponseEntity.ok(created);
    }

    // Get activity by ID
    @GetMapping("/{id}")
    public ResponseEntity<PetActivity> getActivity(@PathVariable String id) {
        return petActivityService.getActivity(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all activities for a specific pet
    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<PetActivity>> getPetActivities(@PathVariable String petId) {
        List<PetActivity> activities = petActivityService.getPetActivities(petId);
        return ResponseEntity.ok(activities);
    }

    // Get all activities for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetActivity>> getUserActivities(@PathVariable String userId) {
        List<PetActivity> activities = petActivityService.getUserActivities(userId);
        return ResponseEntity.ok(activities);
    }

    // Get activities by type
    @GetMapping("/type/{activityType}")
    public ResponseEntity<List<PetActivity>> getActivitiesByType(@PathVariable PetActivity.ActivityType activityType) {
        List<PetActivity> activities = petActivityService.getActivitiesByType(activityType);
        return ResponseEntity.ok(activities);
    }

    // Get activities within date range
    @GetMapping("/date-range")
    public ResponseEntity<List<PetActivity>> getActivitiesByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<PetActivity> activities = petActivityService.getActivitiesByDateRange(startDate, endDate);
        return ResponseEntity.ok(activities);
    }

    // Update activity
    @PutMapping("/{id}")
    public ResponseEntity<PetActivity> updateActivity(@PathVariable String id, 
                                                    @Valid @RequestBody PetActivity activityDetails) {
        return petActivityService.updateActivity(id, activityDetails)
                .map(updated -> {
                    // Publish update event to Kafka
                    kafkaTemplate.send("pet-activity-events", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete activity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable String id) {
        if (petActivityService.deleteActivity(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Get health metrics for a pet
    @GetMapping("/pet/{petId}/health")
    public ResponseEntity<Map<String, Object>> getPetHealthMetrics(@PathVariable String petId) {
        Map<String, Object> healthMetrics = petActivityService.getPetHealthMetrics(petId);
        return ResponseEntity.ok(healthMetrics);
    }

    // Get activity statistics
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getActivityStatistics(
            @RequestParam(required = false) String petId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        
        Map<String, Object> statistics = petActivityService.getActivityStatistics(petId, userId, startDate, endDate);
        return ResponseEntity.ok(statistics);
    }

    // Search activities
    @GetMapping("/search")
    public ResponseEntity<List<PetActivity>> searchActivities(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) PetActivity.ActivityType activityType) {
        
        List<PetActivity> activities = petActivityService.searchActivities(query, tags, activityType);
        return ResponseEntity.ok(activities);
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Pet Activities Service is running");
    }
}
