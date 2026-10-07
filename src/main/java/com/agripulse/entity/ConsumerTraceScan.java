package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumer_trace_scan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerTraceScan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scanId;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private HarvestBatch harvestBatch;

    private String scannedLocation;
    private LocalDateTime scannedAt = LocalDateTime.now();
}