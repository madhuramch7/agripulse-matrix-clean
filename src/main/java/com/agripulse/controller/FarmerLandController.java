package com.agripulse.controller;

import com.agripulse.entity.FarmerProfile;
import com.agripulse.entity.LandPlot;
import com.agripulse.entity.SoilHealthRecord;
import com.agripulse.repository.FarmerProfileRepository;
import com.agripulse.repository.LandPlotRepository;
import com.agripulse.repository.SoilHealthRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer")
@CrossOrigin(origins = "*")
public class FarmerLandController {

    private final FarmerProfileRepository farmerProfileRepository;
    private final LandPlotRepository landPlotRepository;
    private final SoilHealthRepository soilHealthRepository;

    public FarmerLandController(FarmerProfileRepository farmerProfileRepository,
                                LandPlotRepository landPlotRepository,
                                SoilHealthRepository soilHealthRepository) {
        this.farmerProfileRepository = farmerProfileRepository;
        this.landPlotRepository = landPlotRepository;
        this.soilHealthRepository = soilHealthRepository;
    }

    // --- Farmer Profile Endpoints ---

    @PostMapping("/profile")
    public ResponseEntity<FarmerProfile> createOrUpdateProfile(@RequestBody FarmerProfile profile) {
        FarmerProfile savedProfile = farmerProfileRepository.save(profile);
        return ResponseEntity.ok(savedProfile);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<FarmerProfile> getProfileById(@PathVariable Long id) {
        return farmerProfileRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Land Plot Endpoints ---

    @PostMapping("/land-plots")
    public ResponseEntity<LandPlot> createLandPlot(@RequestBody LandPlot landPlot) {
        LandPlot savedPlot = landPlotRepository.save(landPlot);
        return ResponseEntity.ok(savedPlot);
    }

    @GetMapping("/land-plots")
    public ResponseEntity<List<LandPlot>> getAllLandPlots() {
        return ResponseEntity.ok(landPlotRepository.findAll());
    }

    @GetMapping("/land-plots/{id}")
    public ResponseEntity<LandPlot> getLandPlotById(@PathVariable Long id) {
        return landPlotRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Soil Health Endpoints ---

    @PostMapping("/soil-health")
    public ResponseEntity<SoilHealthRecord> recordSoilHealth(@RequestBody SoilHealthRecord soilRecord) {
        SoilHealthRecord savedRecord = soilHealthRepository.save(soilRecord);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/soil-health")
    public ResponseEntity<List<SoilHealthRecord>> getAllSoilRecords() {
        return ResponseEntity.ok(soilHealthRepository.findAll());
    }

    @GetMapping("/soil-health/{id}")
    public ResponseEntity<SoilHealthRecord> getSoilRecordById(@PathVariable Long id) {
        return soilHealthRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
