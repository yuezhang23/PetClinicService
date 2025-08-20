package main.java.com.example.WeePetClinic.controller;

import main.java.com.example.WeePetClinic.model.ClinicLocation;
import main.java.com.example.WeePetClinic.model.PetLocation;
import main.java.com.example.WeePetClinic.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maps")
@CrossOrigin(origins = "*")
public class MapsController {
    
    @Autowired
    private MapsService mapsService;
    
    // Clinic Location Endpoints
    
    @GetMapping("/clinics")
    public ResponseEntity<Page<ClinicLocation>> getAllClinics(Pageable pageable) {
        Page<ClinicLocation> clinics = mapsService.getAllClinics(pageable);
        return ResponseEntity.ok(clinics);
    }
    
    @GetMapping("/clinics/{id}")
    public ResponseEntity<ClinicLocation> getClinicById(@PathVariable Long id) {
        ClinicLocation clinic = mapsService.getClinicById(id);
        if (clinic != null) {
            return ResponseEntity.ok(clinic);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/clinics/search")
    public ResponseEntity<List<ClinicLocation>> searchClinicsByLocation(
            @RequestParam BigDecimal latitude,
            @RequestParam BigDecimal longitude,
            @RequestParam(defaultValue = "10.0") Double radiusKm) {
        
        List<ClinicLocation> clinics = mapsService.findClinicsNearLocation(latitude, longitude, radiusKm);
        return ResponseEntity.ok(clinics);
    }
    
    @GetMapping("/clinics/city/{city}")
    public ResponseEntity<List<ClinicLocation>> getClinicsByCity(@PathVariable String city) {
        List<ClinicLocation> clinics = mapsService.getClinicsByCity(city);
        return ResponseEntity.ok(clinics);
    }
    
    @GetMapping("/clinics/services")
    public ResponseEntity<List<ClinicLocation>> getClinicsByService(
            @RequestParam String service) {
        List<ClinicLocation> clinics = mapsService.getClinicsByService(service);
        return ResponseEntity.ok(clinics);
    }
    
    @PostMapping("/clinics")
    public ResponseEntity<ClinicLocation> createClinic(@Valid @RequestBody ClinicLocation clinic) {
        ClinicLocation createdClinic = mapsService.createClinic(clinic);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClinic);
    }
    
    @PutMapping("/clinics/{id}")
    public ResponseEntity<ClinicLocation> updateClinic(
            @PathVariable Long id, 
            @Valid @RequestBody ClinicLocation clinic) {
        ClinicLocation updatedClinic = mapsService.updateClinic(id, clinic);
        if (updatedClinic != null) {
            return ResponseEntity.ok(updatedClinic);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/clinics/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        boolean deleted = mapsService.deleteClinic(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Pet Location Endpoints
    
    @GetMapping("/pets/{petId}/locations")
    public ResponseEntity<Page<PetLocation>> getPetLocations(
            @PathVariable String petId, 
            Pageable pageable) {
        Page<PetLocation> locations = mapsService.getPetLocations(petId, pageable);
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/pets/{petId}/locations/recent")
    public ResponseEntity<List<PetLocation>> getRecentPetLocations(
            @PathVariable String petId,
            @RequestParam(defaultValue = "10") int limit) {
        List<PetLocation> locations = mapsService.getRecentPetLocations(petId, limit);
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/pets/{petId}/activities")
    public ResponseEntity<Map<String, Object>> getPetActivitySummary(
            @PathVariable String petId,
            @RequestParam(defaultValue = "7") int days) {
        Map<String, Object> summary = mapsService.getPetActivitySummary(petId, days);
        return ResponseEntity.ok(summary);
    }
    
    @PostMapping("/pets/locations")
    public ResponseEntity<PetLocation> recordPetLocation(@Valid @RequestBody PetLocation location) {
        PetLocation recordedLocation = mapsService.recordPetLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(recordedLocation);
    }
    
    @PostMapping("/pets/locations/batch")
    public ResponseEntity<List<PetLocation>> recordPetLocations(
            @Valid @RequestBody List<PetLocation> locations) {
        List<PetLocation> recordedLocations = mapsService.recordPetLocations(locations);
        return ResponseEntity.status(HttpStatus.CREATED).body(recordedLocations);
    }
    
    // Route Planning Endpoints
    
    @GetMapping("/routes/clinic-to-clinic")
    public ResponseEntity<Map<String, Object>> getRouteBetweenClinics(
            @RequestParam Long fromClinicId,
            @RequestParam Long toClinicId,
            @RequestParam(defaultValue = "driving") String mode) {
        
        Map<String, Object> route = mapsService.getRouteBetweenClinics(fromClinicId, toClinicId, mode);
        return ResponseEntity.ok(route);
    }
    
    @GetMapping("/routes/user-to-clinic")
    public ResponseEntity<Map<String, Object>> getRouteToClinic(
            @RequestParam BigDecimal userLat,
            @RequestParam BigDecimal userLng,
            @RequestParam Long clinicId,
            @RequestParam(defaultValue = "driving") String mode) {
        
        Map<String, Object> route = mapsService.getRouteToClinic(userLat, userLng, clinicId, mode);
        return ResponseEntity.ok(route);
    }
    
    // Analytics Endpoints
    
    @GetMapping("/analytics/clinics/popular")
    public ResponseEntity<List<Map<String, Object>>> getPopularClinics(
            @RequestParam(defaultValue = "10") int limit) {
        List<Map<String, Object>> popularClinics = mapsService.getPopularClinics(limit);
        return ResponseEntity.ok(popularClinics);
    }
    
    @GetMapping("/analytics/pets/activity-heatmap")
    public ResponseEntity<Map<String, Object>> getPetActivityHeatmap(
            @RequestParam String petId,
            @RequestParam(defaultValue = "30") int days) {
        Map<String, Object> heatmap = mapsService.getPetActivityHeatmap(petId, days);
        return ResponseEntity.ok(heatmap);
    }
    
    // Health Check
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "maps-service"));
    }
}
