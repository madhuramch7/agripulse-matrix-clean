package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "disaster_relief_claim")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisasterReliefClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "plot_id", nullable = false)
    private LandPlot landPlot;

    private String disasterType; // FLOOD, DROUGHT, HAILSTORM
    private Double estimatedDamagePct;
    private Double payoutAmount;
    private String claimStatus; // SIMULATED, APPROVED, DISBURSED
    private LocalDateTime triggeredAt = LocalDateTime.now();
}