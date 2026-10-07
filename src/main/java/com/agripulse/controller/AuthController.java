package com.agripulse.controller;

import com.agripulse.dto.AuthRequest;
import com.agripulse.dto.AuthResponse;
import com.agripulse.entity.User;
import com.agripulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("authenticated", false));
        }

        String email = principal.getAttribute("email");
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found in database"));
        }

        User user = userOpt.get();
        return ResponseEntity.ok(Map.of(
            "authenticated", true,
            "email", user.getEmail(),
            "name", user.getName(),
            "picture", user.getPictureUrl() != null ? user.getPictureUrl() : "",
            "hasCompletedOnboarding", user.isHasCompletedOnboarding()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        // Fallback REST login/validation endpoint if needed
        boolean exists = userRepository.existsByEmail(authRequest.getEmail());
        if (!exists) {
            return ResponseEntity.status(401).body(Map.of("error", "User not registered"));
        }
        return ResponseEntity.ok(new AuthResponse("Login successful"));
    }
}