package com.agripulse.controller;

import com.agripulse.entity.*;
import com.agripulse.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agri")
@CrossOrigin(origins = "*")
public class AgriServiceController {

    private final CropCycleRepository cropCycleRepository;
    private final HarvestBatchRepository harvestBatchRepository;
    private final MarketPriceIndexRepository marketPriceIndexRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final ConsumerTraceScanRepository consumerTraceScanRepository;
    private final DisasterReliefClaimRepository disasterReliefClaimRepository;

    public AgriServiceController(CropCycleRepository cropCycleRepository,
                                 HarvestBatchRepository harvestBatchRepository,
                                 MarketPriceIndexRepository marketPriceIndexRepository,
                                 WeatherDataRepository weatherDataRepository,
                                 ConsumerTraceScanRepository consumerTraceScanRepository,
                                 DisasterReliefClaimRepository disasterReliefClaimRepository) {
        this.cropCycleRepository = cropCycleRepository;
        this.harvestBatchRepository = harvestBatchRepository;
        this.marketPriceIndexRepository = marketPriceIndexRepository;
        this.weatherDataRepository = weatherDataRepository;
        this.consumerTraceScanRepository = consumerTraceScanRepository;
        this.disasterReliefClaimRepository = disasterReliefClaimRepository;
    }

    // --- Crop Cycles ---

    @PostMapping("/crop-cycles")
    public ResponseEntity<CropCycle> createCropCycle(@RequestBody CropCycle cropCycle) {
        return ResponseEntity.ok(cropCycleRepository.save(cropCycle));
    }

    @GetMapping("/crop-cycles")
    public ResponseEntity<List<CropCycle>> getAllCropCycles() {
        return ResponseEntity.ok(cropCycleRepository.findAll());
    }

    // --- Harvest Batches ---

    @PostMapping("/harvest-batches")
    public ResponseEntity<HarvestBatch> recordHarvest(@RequestBody HarvestBatch harvestBatch) {
        return ResponseEntity.ok(harvestBatchRepository.save(harvestBatch));
    }

    @GetMapping("/harvest-batches")
    public ResponseEntity<List<HarvestBatch>> getAllHarvests() {
        return ResponseEntity.ok(harvestBatchRepository.findAll());
    }

    // --- Market Prices ---

    @GetMapping("/market-prices")
    public ResponseEntity<List<MarketPriceIndex>> getMarketPrices() {
        return ResponseEntity.ok(marketPriceIndexRepository.findAll());
    }

    // --- Weather Data ---

    @PostMapping("/weather-log")
    public ResponseEntity<WeatherDataLog> logWeather(@RequestBody WeatherDataLog weatherData) {
        return ResponseEntity.ok(weatherDataRepository.save(weatherData));
    }

    @GetMapping("/weather-log")
    public ResponseEntity<List<WeatherDataLog>> getWeatherLogs() {
        return ResponseEntity.ok(weatherDataRepository.findAll());
    }

    // --- Consumer Trace Scan ---

    @PostMapping("/trace-scan")
    public ResponseEntity<ConsumerTraceScan> recordTraceScan(@RequestBody ConsumerTraceScan scan) {
        return ResponseEntity.ok(consumerTraceScanRepository.save(scan));
    }

    // --- Disaster Simulation Trigger ---

    @PostMapping("/simulate-disaster/{claimId}")
    public ResponseEntity<DisasterReliefClaim> triggerDisasterSimulation(@PathVariable Long claimId) {
        return disasterReliefClaimRepository.findById(claimId)
                .map(claim -> {
                    claim.setClaimStatus("APPROVED");
                    DisasterReliefClaim updatedClaim = disasterReliefClaimRepository.save(claim);
                    return ResponseEntity.ok(updatedClaim);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
