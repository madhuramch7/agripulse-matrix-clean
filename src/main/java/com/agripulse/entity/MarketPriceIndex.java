package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "market_price_index")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketPriceIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;

    private String commodityName;
    private String marketMandi;
    private Double modalPricePerQuintal;
    private LocalDate priceDate;
}