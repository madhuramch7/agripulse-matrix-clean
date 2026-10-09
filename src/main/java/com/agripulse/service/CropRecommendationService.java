package com.agripulse.service;

import com.agripulse.dto.SoilDiagnosisResponse;
import com.agripulse.entity.CropRecommendationRecord;
import com.agripulse.repository.CropRecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CropRecommendationService {

    private final CropRecommendationRepository repository;

    public CropRecommendationService(CropRecommendationRepository repository) {
        this.repository = repository;
    }

    public List<CropRecommendationRecord> getAllRecommendations() {
        return repository.findAll();
    }

    public List<CropRecommendationRecord> getRecommendationsByCrop(String label) {
        return repository.findAll().stream()
                .filter(r -> r.getLabel() != null && r.getLabel().equalsIgnoreCase(label))
                .toList();
    }

    public String predictBestCrop(Double n, Double p, Double k, Double temp, Double humidity, Double ph, Double rainfall) {
        List<CropRecommendationRecord> records = repository.findAll();
        if (records.isEmpty()) return "No data available";

        CropRecommendationRecord bestMatch = null;
        double minDistance = Double.MAX_VALUE;

        for (CropRecommendationRecord r : records) {
            // Euclidean distance formula normalized across features
            double distance = Math.pow(r.getNitrogen() - n, 2) +
                              Math.pow(r.getPhosphorus() - p, 2) +
                              Math.pow(r.getPotassium() - k, 2) +
                              Math.pow(r.getTemperature() - temp, 2) +
                              Math.pow(r.getHumidity() - humidity, 2) +
                              Math.pow(r.getPh() - ph, 2) +
                              Math.pow(r.getRainfall() - rainfall, 2);

            if (distance < minDistance) {
                minDistance = distance;
                bestMatch = r;
            }
        }

        return bestMatch != null ? bestMatch.getLabel() : "Unknown";
    }

    public SoilDiagnosisResponse diagnoseFarmerSoil(Long farmerId, Double n, Double p, Double k, Double temp, Double humidity, Double ph, Double rainfall) {
        SoilDiagnosisResponse response = new SoilDiagnosisResponse();
        response.setFarmerId(farmerId);
        response.setNitrogen(n);
        response.setPhosphorus(p);
        response.setPotassium(k);
        response.setTemperature(temp);
        response.setHumidity(humidity);
        response.setPh(ph);
        response.setRainfall(rainfall);

        // Evaluate nutrient statuses
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("nitrogen", n < 40 ? "Deficient" : (n > 140 ? "Excessive" : "Optimal"));
        statusMap.put("phosphorus", p < 20 ? "Deficient" : (p > 90 ? "Excessive" : "Optimal"));
        statusMap.put("potassium", k < 25 ? "Deficient" : (k > 200 ? "Excessive" : "Optimal"));
        statusMap.put("ph", ph < 5.5 ? "Too Acidic" : (ph > 7.5 ? "Too Alkaline" : "Optimal"));
        response.setNutrientStatus(statusMap);

        // Predict best crop using our similarity engine
        String bestCrop = predictBestCrop(n, p, k, temp, humidity, ph, rainfall);
        response.setRecommendedCrop(bestCrop);

        // Calculate health score (0-100) based on optimal parameters
        int optimalCount = 0;
        for (String status : statusMap.values()) {
            if ("Optimal".equals(status)) optimalCount++;
        }
        int healthScore = (int) (((double) optimalCount / statusMap.size()) * 100);
        response.setOverallHealthScore(Math.max(40, healthScore));

        return response;
    }
}