package com.agripulse.controller;

import com.agripulse.service.AiAssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiAssistantService aiAssistantService;

    public AiController(AiAssistantService aiAssistantService) {
        this.aiAssistantService = aiAssistantService;
    }

    @PostMapping("/chat")
    public ResponseEntity<?> chatWithAi(@RequestBody Map<String, String> request, Principal principal) {
        String userMessage = request.get("message");
        String languageCode = request.getOrDefault("language", "hi");

        if (userMessage == null || userMessage.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty"));
        }

        String userEmail = principal != null ? principal.getName() : "anonymous";
        String aiResponse = aiAssistantService.generateAdvice(userEmail, userMessage, languageCode);

        return ResponseEntity.ok(Map.of(
            "response", aiResponse,
            "language", languageCode
        ));
    }
}