package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crop_yield_prediction_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropYieldPredictionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crop;
    private String cropYear;
    private String season;
    private String state;
    private String district;
    private Double area;
    private Double production;
    private Double yield;
}