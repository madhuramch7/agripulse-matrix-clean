package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "market_price_index")
public class MarketPriceIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String state;
    private String district;
    private String market;
    private String commodity;
    private BigDecimal modalPrice;
    private LocalDate arrivalDate;
}