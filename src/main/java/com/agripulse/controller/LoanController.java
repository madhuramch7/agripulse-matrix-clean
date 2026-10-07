package com.agripulse.controller;

import com.agripulse.entity.LoanSchemeMaster;
import com.agripulse.repository.LoanSchemeRepository;
import com.agripulse.service.ApciService;
import com.agripulse.service.BankRedirectService;
import com.agripulse.service.LoanMatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanSchemeRepository loanSchemeRepository;
    private final LoanMatchService loanMatchService;
    private final ApciService apciService;
    private final BankRedirectService bankRedirectService;

    public LoanController(LoanSchemeRepository loanSchemeRepository, 
                          LoanMatchService loanMatchService,
                          ApciService apciService,
                          BankRedirectService bankRedirectService) {
        this.loanSchemeRepository = loanSchemeRepository;
        this.loanMatchService = loanMatchService;
        this.apciService = apciService;
        this.bankRedirectService = bankRedirectService;
    }

    @GetMapping("/schemes")
    public ResponseEntity<List<LoanSchemeMaster>> getAllSchemes() {
        return ResponseEntity.ok(loanSchemeRepository.findAll());
    }

    @GetMapping("/apci/{farmerId}")
    public ResponseEntity<?> getApciScore(@PathVariable Long farmerId) {
        double score = apciService.calculateApciScore(farmerId);
        return ResponseEntity.ok(Map.of("farmerId", farmerId, "apciScore", score));
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyForLoan(@RequestBody Map<String, Object> request) {
        Long farmerId = Long.valueOf(request.getOrDefault("farmerId", 1L).toString());
        Long schemeId = Long.valueOf(request.getOrDefault("schemeId", 1L).toString());
        BigDecimal amount = new BigDecimal(request.getOrDefault("amount", "50000").toString());

        String redirectUrl = bankRedirectService.generateBankRedirectUrl(farmerId, schemeId, amount);
        return ResponseEntity.ok(Map.of("redirectUrl", redirectUrl));
    }
}