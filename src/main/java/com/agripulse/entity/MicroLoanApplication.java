package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class MicroLoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private FarmerProfile farmerProfile;

    @ManyToOne
    @JoinColumn(name = "scheme_id")
    private LoanSchemeMaster loanScheme;

    private Double requestedAmount;
    private String applicationStatus;

    @Column(length = 2000) // Extends column size from 255 to 2000 characters
    private String signedJwtPayload;

    private LocalDateTime appliedAt = LocalDateTime.now();
}