package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long farmerId;

    private String fullName;
    private String aadhaarNumberHash;
    private Integer creditBureauScore;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;
}