package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "land_plot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandPlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plotId;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private FarmerProfile farmerProfile;

    private Double areaInAcres;
    private String khatauniNumber;
    private String geoCoordinates;
    private String soilType;
}