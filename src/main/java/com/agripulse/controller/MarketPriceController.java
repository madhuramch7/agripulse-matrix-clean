package com.agripulse.controller;

import com.agripulse.entity.MarketPrice;
import com.agripulse.repository.MarketPriceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-prices")
@CrossOrigin(origins = "*") // Allows fetch requests from frontend pages
public class MarketPriceController {

    private final MarketPriceRepository repository;

    public MarketPriceController(MarketPriceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<MarketPrice> getAllMarketPrices() {
        return repository.findAll();
    }
}