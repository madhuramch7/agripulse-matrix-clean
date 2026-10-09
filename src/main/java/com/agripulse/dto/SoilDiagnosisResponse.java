package com.agripulse.dto;

import java.util.Map;

public class SoilDiagnosisResponse {
    private Long farmerId;
    private Double nitrogen;
    private Double phosphorus;
    private Double potassium;
    private Double ph;
    private Double rainfall;
    private Double temperature;
    private Double humidity;
    private Map<String, String> nutrientStatus; // e.g., "Optimal", "Deficient"
    private String recommendedCrop;
    private int overallHealthScore; // Scale of 0-100

    public SoilDiagnosisResponse() {}

    // Getters and Setters
    public Long getFarmerId() { return farmerId; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }

    public Double getNitrogen() { return nitrogen; }
    public void setNitrogen(Double nitrogen) { this.nitrogen = nitrogen; }

    public Double getPhosphorus() { return phosphorus; }
    public void setPhosphorus(Double phosphorus) { this.phosphorus = phosphorus; }

    public Double getPotassium() { return potassium; }
    public void setPotassium(Double potassium) { this.potassium = potassium; }

    public Double getPh() { return ph; }
    public void setPh(Double ph) { this.ph = ph; }

    public Double getRainfall() { return rainfall; }
    public void setRainfall(Double rainfall) { this.rainfall = rainfall; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public Map<String, String> getNutrientStatus() { return nutrientStatus; }
    public void setNutrientStatus(Map<String, String> nutrientStatus) { this.nutrientStatus = nutrientStatus; }

    public String getRecommendedCrop() { return recommendedCrop; }
    public void setRecommendedCrop(String recommendedCrop) { this.recommendedCrop = recommendedCrop; }

    public int getOverallHealthScore() { return overallHealthScore; }
    public void setOverallHealthScore(int overallHealthScore) { this.overallHealthScore = overallHealthScore; }
}