package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "farmer_profile")
public class FarmerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private String fullName;
    private String mobileHash;
    private String preferredLanguage;
    private String state;
    private String district;
    private String village;
    private Integer farmingExperienceYears;
    private String irrigationSource;
    private String primaryCrops;
    private BigDecimal loanNeedAmount;
    private Integer bureauScore;
    private String aadhaarHash;
    private String panHash;
    private String bankAccountNo;
}