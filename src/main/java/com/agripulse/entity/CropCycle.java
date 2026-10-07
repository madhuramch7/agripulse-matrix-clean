package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "crop_cycle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cycleId;

    @ManyToOne
    @JoinColumn(name = "plot_id", nullable = false)
    private LandPlot landPlot;

    private String cropName;
    private String season; // KHARIF, RABI, ZAID
    private LocalDate sowingDate;
    private LocalDate expectedHarvestDate;
    private String status; // PLANTED, HARVESTED, DESTROYED
}