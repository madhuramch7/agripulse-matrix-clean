package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "crop_master")
public class CropMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String label;
    private Double nMean;
    private Double nStdDev;
    private Double pMean;
    private Double pStdDev;
    private Double kMean;
    private Double kStdDev;
    private Double tempMean;
    private Double tempStdDev;
    private Double humidityMean;
    private Double humidityStdDev;
    private Double phMean;
    private Double phStdDev;
    private Double rainfallMean;
    private Double rainfallStdDev;
}