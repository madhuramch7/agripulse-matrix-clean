package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "loan_scheme_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanSchemeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schemeId;

    @Column(nullable = false)
    private String schemeName;

    private String bankName;
    private Double maxAmount;
    private Double interestRatePct;
    private Integer minApciScore;
    private String description;
}