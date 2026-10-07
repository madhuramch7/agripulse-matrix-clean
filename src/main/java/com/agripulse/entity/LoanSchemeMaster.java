package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_scheme_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanSchemeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schemeName;
    private Double minApci;

    @Column(precision = 12, scale = 2)
    private BigDecimal maxAmount;

    private Double interestRate;
    private Integer tenureMonths;

    @Column(name = "bank_id")
    private Long bankId;
}