package com.agripulse.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crop_recommendation_records")
public class CropRecommendationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nitrogen;
    private Double phosphorus;
    private Double potassium;
    private Double temperature;
    private Double humidity;
    private Double ph;
    private Double rainfall;
    private String label;

    public CropRecommendationRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getNitrogen() { return nitrogen; }
    public void setNitrogen(Double nitrogen) { this.nitrogen = nitrogen; }

    public Double getPhosphorus() { return phosphorus; }
    public void setPhosphorus(Double phosphorus) { this.phosphorus = phosphorus; }

    public Double getPotassium() { return potassium; }
    public void setPotassium(Double potassium) { this.potassium = potassium; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public Double getPh() { return ph; }
    public void setPh(Double ph) { this.ph = ph; }

    public Double getRainfall() { return rainfall; }
    public void setRainfall(Double rainfall) { this.rainfall = rainfall; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}