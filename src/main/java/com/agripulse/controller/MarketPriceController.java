package com.agripulse.controller;

import com.agripulse.entity.MarketPriceIndex;
import com.agripulse.repository.MarketPriceIndexRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-prices")
public class MarketPriceController {

    private final MarketPriceIndexRepository marketPriceIndexRepository;

    public MarketPriceController(MarketPriceIndexRepository marketPriceIndexRepository) {
        this.marketPriceIndexRepository = marketPriceIndexRepository;
    }

    @GetMapping
    public ResponseEntity<List<MarketPriceIndex>> getMarketPrices(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String commodity) {
        
        List<MarketPriceIndex> prices = marketPriceIndexRepository.findAll();
        // Return filtered list or all records as baseline
        return ResponseEntity.ok(prices);
    }
}