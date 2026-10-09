package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "disaster_relief_claim")
public class DisasterReliefClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "plot_id")
private LandPlot landPlot;
    
    private String disasterType;
    private Double estimatedDamagePct;
    private Double payoutAmount;
    private String claimStatus;
    private LocalDateTime triggeredAt;
}