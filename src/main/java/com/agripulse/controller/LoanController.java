package com.agripulse.controller;

import com.agripulse.entity.LoanSchemeMaster;
import com.agripulse.repository.LoanSchemeRepository;
import com.agripulse.service.ApciService;
import com.agripulse.service.BankRedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Exposes the APCI credit scoring and bank-partner loan matching services
 * (ApciService, BankRedirectService) over REST. Previously these services
 * existed but had no controller, so /loans.html and /sbi-portal.html had
 * nothing to call.
 */
@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LoanController {

    private final ApciService apciService;
    private final BankRedirectService bankRedirectService;
    private final LoanSchemeRepository loanSchemeRepository;

    // --- APCI Score ---

    @GetMapping("/apci/{farmerId}")
    public ResponseEntity<Map<String, Object>> getApciScore(@PathVariable Long farmerId) {
        Integer score = apciService.calculateApciScore(farmerId);
        return ResponseEntity.ok(Map.of("farmerId", farmerId, "apciScore", score));
    }

    // --- Matched Loan Schemes (based on current APCI score) ---

    @GetMapping("/schemes/{farmerId}")
    public ResponseEntity<List<LoanSchemeMaster>> getMatchedSchemes(@PathVariable Long farmerId) {
        Integer score = apciService.calculateApciScore(farmerId);
        return ResponseEntity.ok(loanSchemeRepository.findByMinApciScoreLessThanEqual(score));
    }

    @GetMapping("/schemes")
    public ResponseEntity<List<LoanSchemeMaster>> getAllSchemes() {
        return ResponseEntity.ok(loanSchemeRepository.findAll());
    }

    // --- Apply: generates signed JWT hand-off and redirect URL to the partner bank portal ---

    @PostMapping("/apply")
    public ResponseEntity<Map<String, String>> applyForLoan(@RequestBody Map<String, Long> request) {
        Long farmerId = request.get("farmerId");
        Long schemeId = request.get("schemeId");
        String redirectUrl = bankRedirectService.generateBankRedirectUrl(farmerId, schemeId);
        return ResponseEntity.ok(Map.of("redirectUrl", redirectUrl));
    }
}
