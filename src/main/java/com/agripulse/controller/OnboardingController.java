package com.agripulse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/onboarding")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class OnboardingController {

    // Fixed: changed OnboardingController.java to OnboardingController.class
    private static final Logger log = LoggerFactory.getLogger(OnboardingController.class);

    public static class FarmerOnboardingRequest {
        private String fullName;
        private String aadharNumber;
        private String contactNumber;
        private String village;
        private String state;
        private Object landSizeAcres;
        private Object annualIncome;
        private String cropType;

        // Getters
        public String getFullName() { return fullName; }
        public String getAadharNumber() { return aadharNumber; }
        public String getContactNumber() { return contactNumber; }
        public String getVillage() { return village; }
        public String getState() { return state; }
        public Object getLandSizeAcres() { return landSizeAcres; }
        public Object getAnnualIncome() { return annualIncome; }
        public String getCropType() { return cropType; }

        // Setters
        public void setFullName(String fullName) { this.fullName = fullName; }
        public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }
        public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
        public void setVillage(String village) { this.village = village; }
        public void setState(String state) { this.state = state; }
        public void setLandSizeAcres(Object landSizeAcres) { this.landSizeAcres = landSizeAcres; }
        public void setAnnualIncome(Object annualIncome) { this.annualIncome = annualIncome; }
        public void setCropType(String cropType) { this.cropType = cropType; }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerFarmer(@RequestBody(required = false) FarmerOnboardingRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (request == null) {
            response.put("success", false);
            response.put("message", "Request body cannot be empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String fullName = Optional.ofNullable(request.getFullName())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElse("Unknown Farmer");

        String aadhar = Optional.ofNullable(request.getAadharNumber())
                .map(String::trim)
                .orElse("");

        if (!aadhar.matches("^\\d{12}$")) {
            response.put("success", false);
            response.put("message", "Invalid identifier format. Expected 12 digits.");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }

        BigDecimal landSize = parseBigDecimal(request.getLandSizeAcres(), BigDecimal.ZERO);
        BigDecimal income = parseBigDecimal(request.getAnnualIncome(), BigDecimal.ZERO);

        String crop = Optional.ofNullable(request.getCropType())
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElse("Paddy");

        log.info("Processing onboarding for farmer: {} with land: {} acres", fullName, landSize);

        response.put("success", true);
        response.put("farmerId", "APM-" + System.currentTimeMillis());
        response.put("registeredName", fullName);
        response.put("landSizeAcres", landSize);
        response.put("annualIncome", income);
        response.put("cropType", crop);
        response.put("status", "VERIFIED");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private BigDecimal parseBigDecimal(Object value, BigDecimal fallback) {
        if (value == null) return fallback;
        try {
            String strVal = String.valueOf(value).replaceAll("[^0-9.]", "").trim();
            if (strVal.isEmpty()) return fallback;
            return new BigDecimal(strVal);
        } catch (Exception ex) {
            log.warn("Failed to parse numeric input: {}. Using fallback: {}", value, fallback);
            return fallback;
        }
    }
}