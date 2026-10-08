package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "farmer_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String fullName;
    private String mobileHash;
    private String preferredLanguage;
    private String state;
    private String district;
    private String village;
    private Integer farmingExperienceYears;
    private String irrigationSource;
    private String primarycrops;
    private BigDecimal farmSize;
    private String soilType;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal loanNeedAmount;
    
    private Integer bureauScore;
    private String aadhaarHash;
    private String panHash;
    private String bankAccountNo;

    // Helper getter for ApciService compatibility
    public Integer getBureauScore() {
        return bureauScore != null ? bureauScore : 300;
    }
}