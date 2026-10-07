package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "harvest_batch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarvestBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @ManyToOne
    @JoinColumn(name = "cycle_id", nullable = false)
    private CropCycle cropCycle;

    private Double quantityInQuintals;
    private LocalDate harvestDate;
    private String qrCodeHash;
}