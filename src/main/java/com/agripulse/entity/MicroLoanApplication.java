package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "micro_loan_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroLoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(name = "scheme_id")
    private Long schemeId;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    private String status;
    private String jwtRef;
}