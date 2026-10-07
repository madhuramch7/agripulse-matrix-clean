package com.agripulse.service;

import com.agripulse.entity.LoanSchemeMaster;
import com.agripulse.repository.LoanSchemeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanMatchService {

    private final LoanSchemeRepository loanSchemeRepository;

    public LoanMatchService(LoanSchemeRepository loanSchemeRepository) {
        this.loanSchemeRepository = loanSchemeRepository;
    }

    public List<LoanSchemeMaster> getEligibleSchemes(double farmerApci, BigDecimal requestedAmount) {
        return loanSchemeRepository.findAll().stream()
                .filter(scheme -> farmerApci >= scheme.getMinApci())
                .filter(scheme -> requestedAmount.compareTo(scheme.getMaxAmount()) <= 0)
                .collect(Collectors.toList());
    }
}