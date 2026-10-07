package com.agripulse.service;

import com.agripulse.entity.ApciCalculationLog;
import com.agripulse.entity.FarmerProfile;
import com.agripulse.entity.LandPlot;
import com.agripulse.entity.SoilHealthRecord;
import com.agripulse.entity.WeatherDataLog;
import com.agripulse.repository.ApciCalculationLogRepository;
import com.agripulse.repository.FarmerProfileRepository;
import com.agripulse.repository.LandPlotRepository;
import com.agripulse.repository.SoilHealthRepository;
import com.agripulse.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApciService {

    private final FarmerProfileRepository farmerProfileRepository;
    private final LandPlotRepository landPlotRepository;
    private final SoilHealthRepository soilHealthRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final ApciCalculationLogRepository apciLogRepository;

    /**
     * Calculates the APCI credit score for a given farmer ID.
     */
    public Integer calculateApciScore(Long farmerId) {
        FarmerProfile farmer = farmerProfileRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer Profile not found for ID: " + farmerId));

        // 1. Traditional Credit Bureau Factor (Normalized to 0 - 100)
        double bureauScore = (farmer.getCreditBureauScore() != null) ? farmer.getCreditBureauScore() : 600.0;
        double normalizedBureauFactor = Math.min(100.0, Math.max(0.0, (bureauScore - 300) / 5.5));

        // 2. Agronomic Soil Health Factor (0 - 100)
        double soilHealthFactor = calculateSoilHealthFactor(farmerId);

        // 3. Land Scale & Capability Factor (0 - 100)
        double landScaleFactor = calculateLandScaleFactor(farmerId);

        // 4. Regional Climate Risk Penalty Factor (0 - 50)
        double climateRiskPenalty = calculateClimateRiskPenalty("REGION_01");

        // Weighted Calculation
        double rawScore = (normalizedBureauFactor * 0.40) + 
                         (soilHealthFactor * 0.35) + 
                         (landScaleFactor * 0.25);

        // Map to standard 300 - 850 credit range
        int finalScore = (int) Math.round(300 + (rawScore * 5.5) - climateRiskPenalty);
        finalScore = Math.max(300, Math.min(850, finalScore)); // Clamp between 300 and 850

        // Log calculation audit entry into DB
        ApciCalculationLog log = new ApciCalculationLog();
        log.setFarmerProfile(farmer);
        log.setCalculatedScore(finalScore);
        log.setSoilHealthFactor(soilHealthFactor);
        log.setCreditBureauFactor(normalizedBureauFactor);
        log.setClimateRiskFactor(climateRiskPenalty);
        apciLogRepository.save(log);

        return finalScore;
    }

    private double calculateSoilHealthFactor(Long farmerId) {
        List<LandPlot> plots = landPlotRepository.findByFarmerProfile_FarmerId(farmerId);
        if (plots.isEmpty()) return 50.0; // Default baseline score

        double totalSoilScore = 0.0;
        int recordCount = 0;

        for (LandPlot plot : plots) {
            List<SoilHealthRecord> records = soilHealthRepository.findByLandPlot_PlotId(plot.getPlotId());
            for (SoilHealthRecord record : records) {
                double pH = (record.getPHValue() != null) ? record.getPHValue() : 6.5;
                double moisture = (record.getMoisturePct() != null) ? record.getMoisturePct() : 20.0;

                // Optimal pH is 6.0 to 7.5
                double phScore = (pH >= 6.0 && pH <= 7.5) ? 100.0 : 60.0;
                double moistureScore = Math.min(100.0, moisture * 3.3);

                totalSoilScore += (phScore * 0.5) + (moistureScore * 0.5);
                recordCount++;
            }
        }
        return recordCount > 0 ? (totalSoilScore / recordCount) : 65.0;
    }

    private double calculateLandScaleFactor(Long farmerId) {
        List<LandPlot> plots = landPlotRepository.findByFarmerProfile_FarmerId(farmerId);
        double totalAcres = plots.stream()
                .mapToDouble(plot -> plot.getAreaInAcres() != null ? plot.getAreaInAcres() : 0.0)
                .sum();

        // Standard scaling factor (5+ acres yields maximum scale score)
        return Math.min(100.0, totalAcres * 20.0);
    }

    private double calculateClimateRiskPenalty(String regionCode) {
        List<WeatherDataLog> logs = weatherDataRepository.findByRegionCode(regionCode);
        if (logs.isEmpty()) return 0.0;

        boolean hasExtremeWeather = logs.stream().anyMatch(WeatherDataLog::getIsExtremeFlag);
        return hasExtremeWeather ? 25.0 : 5.0;
    }
}