package com.agripulse.controller;

import com.agripulse.entity.FarmerProfile;
import com.agripulse.entity.UserAccount;
import com.agripulse.repository.FarmerProfileRepository;
import com.agripulse.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/onboarding")
public class OnboardingController {

    private final UserAccountRepository userAccountRepository;
    private final FarmerProfileRepository farmerProfileRepository;

    public OnboardingController(UserAccountRepository userAccountRepository, 
                                FarmerProfileRepository farmerProfileRepository) {
        this.userAccountRepository = userAccountRepository;
        this.farmerProfileRepository = farmerProfileRepository;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        Optional<UserAccount> userOpt = userAccountRepository.findByEmail(principal.getName());
        if (userOpt.isPresent()) {
            boolean complete = userOpt.get().getOnboardingComplete() != null && userOpt.get().getOnboardingComplete();
            return ResponseEntity.ok(Map.of("onboardingComplete", complete));
        }
        return ResponseEntity.status(404).body(Map.of("error", "User not found"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getFarmerProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        Optional<UserAccount> userOpt = userAccountRepository.findByEmail(principal.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }
        
        Optional<FarmerProfile> profileOpt = farmerProfileRepository.findByUserId(userOpt.get().getId());
        if (profileOpt.isPresent()) {
            return ResponseEntity.ok(profileOpt.get());
        }
        return ResponseEntity.status(404).body(Map.of("error", "Profile not found"));
    }

    private static String str(Map<String, Object> m, String def, String... keys) {
        for (String k : keys) {
            Object v = m.get(k);
            if (v != null && !v.toString().isBlank()) return v.toString();
        }
        return def;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitOnboarding(@RequestBody Map<String, Object> request, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized", "message", "Please log in again."));
        }
        try {
            Optional<UserAccount> userOpt = userAccountRepository.findByEmail(principal.getName());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "User not found", "message", "User not found"));
            }
            UserAccount user = userOpt.get();

            FarmerProfile profile = farmerProfileRepository.findByUserId(user.getId()).orElse(new FarmerProfile());
            profile.setUserId(user.getId());
            profile.setFullName(str(request, "Farmer", "fullName"));
            profile.setState(str(request, "Maharashtra", "state"));
            profile.setDistrict(str(request, "Pune", "district"));
            profile.setVillage(str(request, "Goverdhan", "village"));
            profile.setPreferredLanguage(str(request, "hi", "preferredLanguage"));
            profile.setFarmSize(new BigDecimal(str(request, "20", "farmSize", "areaInAcres")));
            profile.setSoilType(str(request, "Black (Regur)", "soilType"));
            profile.setFarmingExperienceYears((int) Double.parseDouble(str(request, "5", "farmingExperienceYears")));
            profile.setIrrigationSource(str(request, "Borewell / Tubewell", "irrigationSource", "irrigationType"));
            profile.setPrimarycrops(str(request, "Wheat", "primarycrops", "primaryCrop"));
            profile.setLoanNeedAmount(new BigDecimal(str(request, "50000", "loanNeedAmount")));
            profile.setBureauScore(720);
            if (profile.getMobileHash() == null) profile.setMobileHash("");
            if (profile.getAadhaarHash() == null) profile.setAadhaarHash("");
            if (profile.getPanHash() == null) profile.setPanHash("");
            if (profile.getBankAccountNo() == null) profile.setBankAccountNo("");

            farmerProfileRepository.save(profile);

            user.setOnboardingComplete(true);
            userAccountRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "Onboarding completed successfully"));
        } catch (Exception e) {
            String msg = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
            return ResponseEntity.status(400).body(Map.of("error", msg, "message", "Onboarding failed: " + msg));
        }
    }
}