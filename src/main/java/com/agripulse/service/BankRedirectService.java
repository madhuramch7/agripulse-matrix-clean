package com.agripulse.service;

import com.agripulse.entity.FarmerProfile;
import com.agripulse.entity.MicroLoanApplication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class BankRedirectService {

    @Value("${JWT_SECRET:defaultSecretKeyWithAtLeast32BytesLengthForHS256Security}")
    private String jwtSecret;

    public String generateBankToken(FarmerProfile farmer, MicroLoanApplication application) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        
        long expirationTime = 15 * 60 * 1000; // 15 minutes
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(farmer.getFullName())
                .claim("farmerId", farmer.getId())
                .claim("farmerName", farmer.getFullName())
                .claim("schemeId", application.getSchemeId())
                .claim("approvedAmount", application.getAmount())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateBankRedirectUrl(Long farmerId, Long schemeId, BigDecimal amount) {
        return "/sbi-portal.html?token=demo_token_" + farmerId + "_" + schemeId;
    }
}