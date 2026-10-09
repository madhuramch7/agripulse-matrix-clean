package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "micro_loan_application")
public class MicroLoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long farmerId;
    private Long schemeId;
    private BigDecimal amount;
    private String status;
    private String jwtRef;
}