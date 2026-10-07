package com.agripulse.controller;

import com.agripulse.dto.OnboardingRequest;
import com.agripulse.entity.FarmProfile;
import com.agripulse.entity.User;
import com.agripulse.repository.FarmProfileRepository;
import com.agripulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final UserRepository userRepository;
    private final FarmProfileRepository farmProfileRepository;

    @PostMapping("/submit")
    public ResponseEntity<?> submitOnboarding(@AuthenticationPrincipal OAuth2User principal,
                                              @RequestBody OnboardingRequest request) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        String email = principal.getAttribute("email");
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }

        User user = userOpt.get();

        FarmProfile profile = new FarmProfile();
        profile.setFarmSize(request.getFarmSize());
        profile.setState(request.getState());
        profile.setDistrict(request.getDistrict());
        profile.setPrimaryCrop(request.getPrimaryCrop());
        profile.setSoilType(request.getSoilType());
        profile.setIrrigationType(request.getIrrigationType());
        profile.setUser(user);

        farmProfileRepository.save(profile);

        user.setHasCompletedOnboarding(true);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Onboarding completed successfully!"));
    }
}