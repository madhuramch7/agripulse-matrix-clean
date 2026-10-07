package com.agripulse.controller;

import com.agripulse.repository.FarmerProfileRepository;
import com.agripulse.repository.UserAccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok().body("{\"status\":\"active\"}");
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitOnboarding(@RequestBody Object request) {
        return ResponseEntity.ok().body("{\"message\":\"Onboarding successfully recorded.\"}");
    }
}