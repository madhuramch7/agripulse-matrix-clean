package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "market_price_index")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;
    private String district;
    private String market;
    private String commodity;
    
    @Column(precision = 12, scale = 2)
    private BigDecimal modalPrice;
    
    private LocalDate arrivalDate;
}