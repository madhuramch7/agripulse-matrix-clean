package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "apci_calculation_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApciCalculationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    private LocalDateTime calculatedAt;
}