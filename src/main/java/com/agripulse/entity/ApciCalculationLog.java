package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "apci_calculation_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApciCalculationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apciLogId;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private FarmerProfile farmerProfile;

    private Integer calculatedScore;
    private Double soilHealthFactor;
    private Double creditBureauFactor;
    private Double climateRiskFactor;
    private LocalDateTime calculatedAt = LocalDateTime.now();
}