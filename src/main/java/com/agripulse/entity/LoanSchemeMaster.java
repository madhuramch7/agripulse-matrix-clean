package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "loan_scheme_master")
public class LoanSchemeMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String schemeName;
    private Double minApci;
    private BigDecimal maxAmount;
    private Double interestRate;
    private Integer tenureMonths;
    private Long bankId;
}