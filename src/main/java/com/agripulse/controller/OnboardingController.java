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

    @PostMapping("/submit")
    public ResponseEntity<?> submitOnboarding(@RequestBody Map<String, Object> request, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        Optional<UserAccount> userOpt = userAccountRepository.findByEmail(principal.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }

        UserAccount user = userOpt.get();

        FarmerProfile profile = new FarmerProfile();
        profile.setUserId(user.getId());
        profile.setFullName((String) request.getOrDefault("fullName", "Farmer"));
        profile.setState((String) request.getOrDefault("state", "Uttar Pradesh"));
        profile.setDistrict((String) request.getOrDefault("district", "Mathura"));
        profile.setVillage((String) request.getOrDefault("village", "Goverdhan"));
        profile.setPreferredLanguage((String) request.getOrDefault("preferredLanguage", "hi"));
        
        Object expObj = request.get("farmingExperienceYears");
        profile.setFarmingExperienceYears(expObj != null ? Integer.parseInt(expObj.toString()) : 5);
        
        profile.setIrrigationSource((String) request.getOrDefault("irrigationSource", "Tubewell"));
        profile.setPrimarycrops((String) request.getOrDefault("primarycrops", "Wheat, Mustard"));
        
        Object loanObj = request.get("loanNeedAmount");
        profile.setLoanNeedAmount(loanObj != null ? new BigDecimal(loanObj.toString()) : new BigDecimal("50000"));
        
        profile.setBureauScore(720); // Default simulated bureau score

        farmerProfileRepository.save(profile);

        // Mark onboarding complete on user account
        user.setOnboardingComplete(true);
        userAccountRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Onboarding completed successfully"));
    }
}