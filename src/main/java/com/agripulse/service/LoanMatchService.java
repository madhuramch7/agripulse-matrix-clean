package com.agripulse.service;

import com.agripulse.entity.LoanSchemeMaster;
import com.agripulse.repository.LoanSchemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanMatchService {

    private final ApciService apciService;
    private final LoanSchemeRepository loanSchemeRepository;

    /**
     * Finds all loan schemes that match or fall below a farmer's calculated APCI credit score.
     */
    public List<LoanSchemeMaster> getEligibleSchemesForFarmer(Long farmerId) {
        // 1. Calculate or retrieve current APCI score
        Integer apciScore = apciService.calculateApciScore(farmerId);

        // 2. Fetch schemes where minApciScore <= farmer's APCI score
        return loanSchemeRepository.findByMinApciScoreLessThanEqual(apciScore);
    }

    /**
     * Retrieves all loan schemes available in the system.
     */
    public List<LoanSchemeMaster> getAllSchemes() {
        return loanSchemeRepository.findAll();
    }
}