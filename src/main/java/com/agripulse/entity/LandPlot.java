package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "land_plot")
public class LandPlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double areaInAcres;
}