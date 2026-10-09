package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "apci_calculation_log")
public class ApciCalculationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long farmerId;
    private BigDecimal score;
    private LocalDateTime calculatedAt;
}