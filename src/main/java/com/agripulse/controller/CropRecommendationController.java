package com.agripulse.controller;

import com.agripulse.entity.CropRecommendationRecord;
import com.agripulse.service.CropRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crop-recommendations")
public class CropRecommendationController {

    private final CropRecommendationService service;

    public CropRecommendationController(CropRecommendationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CropRecommendationRecord>> getAllRecommendations() {
        return ResponseEntity.ok(service.getAllRecommendations());
    }

    @GetMapping("/crop/{label}")
    public ResponseEntity<List<CropRecommendationRecord>> getByCropLabel(@PathVariable String label) {
        return ResponseEntity.ok(service.getRecommendationsByCrop(label));
    }

    @PostMapping("/predict")
    public ResponseEntity<Map<String, String>> predictCrop(@RequestBody Map<String, Double> input) {
        double n = input.getOrDefault("nitrogen", 0.0);
        double p = input.getOrDefault("phosphorus", 0.0);
        double k = input.getOrDefault("potassium", 0.0);
        double temp = input.getOrDefault("temperature", 0.0);
        double humidity = input.getOrDefault("humidity", 0.0);
        double ph = input.getOrDefault("ph", 7.0);
        double rainfall = input.getOrDefault("rainfall", 0.0);

        String predictedCrop = service.predictBestCrop(n, p, k, temp, humidity, ph, rainfall);
        return ResponseEntity.ok(Map.of("recommended_crop", predictedCrop));
    }
}