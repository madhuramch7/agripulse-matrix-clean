package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "agri_bank_partner")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgriBankPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    @Column(nullable = false, unique = true)
    private String bankName;

    private String portalRedirectUrl;
    private String apiSecretKey;
}