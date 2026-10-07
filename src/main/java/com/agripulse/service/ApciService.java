package com.agripulse.service;

import com.agripulse.entity.ApciCalculationLog;
import com.agripulse.entity.FarmerProfile;
import com.agripulse.repository.ApciCalculationLogRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ApciService {

    private final ApciCalculationLogRepository apciCalculationLogRepository;

    public ApciService(ApciCalculationLogRepository apciCalculationLogRepository) {
        this.apciCalculationLogRepository = apciCalculationLogRepository;
    }

    public double calculateApci(FarmerProfile farmer, double soilScore, double landScore, double climateScore) {
        Integer bureau = farmer.getBureauScore() != null ? farmer.getBureauScore() : 300;
        double sBureau = Math.max(0, Math.min(100, (bureau - 300.0) / 5.5));

        double apci = (0.30 * soilScore) + (0.35 * sBureau) + (0.20 * landScore) - (0.15 * climateScore);
        double clampedApci = Math.max(0.0, Math.min(100.0, apci));

        ApciCalculationLog log = new ApciCalculationLog();
        log.setFarmerId(farmer.getId());
        log.setScore(BigDecimal.valueOf(clampedApci));
        log.setCalculatedAt(LocalDateTime.now());
        apciCalculationLogRepository.save(log);

        return clampedApci;
    }

    public double calculateApciScore(Long farmerId) {
        return 75.0; // Default calculation stub for controller wiring
    }
}