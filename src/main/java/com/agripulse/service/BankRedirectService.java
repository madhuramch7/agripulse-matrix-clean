package com.agripulse.service;

import com.agripulse.entity.FarmerProfile;
import com.agripulse.entity.LoanSchemeMaster;
import com.agripulse.entity.MicroLoanApplication;
import com.agripulse.repository.FarmerProfileRepository;
import com.agripulse.repository.LoanSchemeRepository;
import com.agripulse.repository.MicroLoanApplicationRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BankRedirectService {

    private final FarmerProfileRepository farmerProfileRepository;
    private final LoanSchemeRepository loanSchemeRepository;
    private final MicroLoanApplicationRepository applicationRepository;
    private final ApciService apciService;

    private static final String SECRET_KEY_STR = "AgriPulseMatrixSuperSecretKeyForPartnerBankIntegrations32Bytes!";

    public String generateBankRedirectUrl(Long farmerId, Long schemeId) {
        FarmerProfile farmer = farmerProfileRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer profile not found for ID: " + farmerId));

        LoanSchemeMaster scheme = loanSchemeRepository.findById(schemeId)
                .orElseThrow(() -> new RuntimeException("Loan scheme not found for ID: " + schemeId));

        Integer apciScore = apciService.calculateApciScore(farmerId);

        Map<String, Object> claims = new HashMap<>();
        claims.put("farmerId", farmer.getFarmerId());
        claims.put("fullName", farmer.getFullName());
        claims.put("aadhaarHash", farmer.getAadhaarNumberHash());
        claims.put("apciScore", apciScore);
        claims.put("schemeId", scheme.getSchemeId());
        claims.put("schemeName", scheme.getSchemeName());
        claims.put("maxAmount", scheme.getMaxAmount());

        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STR.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject("FARMER_LOAN_HANDOFF")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        MicroLoanApplication application = new MicroLoanApplication();
        application.setFarmerProfile(farmer);
        application.setLoanScheme(scheme);
        application.setRequestedAmount(scheme.getMaxAmount());
        application.setApplicationStatus("REDIRECTED_TO_BANK");
        application.setSignedJwtPayload(token);
        applicationRepository.save(application);

        return "/sbi-portal.html?token=" + token + "&scheme=" + scheme.getSchemeId();
    }
}