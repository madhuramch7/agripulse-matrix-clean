package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "soil_health_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoilHealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soilRecordId;

    @ManyToOne
    @JoinColumn(name = "plot_id", nullable = false)
    private LandPlot landPlot;

    private Double nitrogenPpm;
    private Double phosphorusPpm;
    private Double potassiumPpm;
    private Double pHValue;
    private Double moisturePct;
    private Double erosionIndex;

    private LocalDateTime recordedAt = LocalDateTime.now();
}