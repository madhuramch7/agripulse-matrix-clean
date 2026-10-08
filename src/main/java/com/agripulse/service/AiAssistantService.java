package com.agripulse.service;

import com.agripulse.entity.FarmerProfile;
import com.agripulse.repository.FarmerProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

@Service
public class AiAssistantService {

    @Value("${GEMINI_API_KEY:}")
    private String geminiApiKey;

    private final FarmerProfileRepository farmerProfileRepository;

    public AiAssistantService(FarmerProfileRepository farmerProfileRepository) {
        this.farmerProfileRepository = farmerProfileRepository;
    }

    public String generateAdvice(String userEmail, String userMessage, String languageCode) {
        Optional<FarmerProfile> farmerOpt = farmerProfileRepository.findAll().stream()
                .findFirst(); // Fallback to primary farmer profile for context

        String farmerContext = farmerOpt.map(f -> 
            String.format("Farmer Name: %s, Location: %s, %s, Experience: %s years, Primary Crops: %s, Loan Need: ₹%s",
                f.getFullName(), f.getDistrict(), f.getState(), f.getFarmingExperienceYears(), f.getPrimarycrops(), f.getLoanNeedAmount())
        ).orElse("No specific farmer profile loaded yet.");

        String prompt = String.format(
            "You are AgriPulse AI, an expert agricultural advisor and financial risk assistant for Indian farmers. " +
            "Provide concise, practical advice in language code '%s'.\n" +
            "Context about the farmer: %s\n" +
            "Farmer Question: %s",
            languageCode, farmerContext, userMessage
        );

        if (geminiApiKey == null || geminiApiKey.isBlank() || geminiApiKey.equals("your_gemini_api_key")) {
            return "[Simulated Gemini Response] Based on your profile and local mandi trends, ensure timely irrigation for your crops and monitor market price indexes before selling.";
        }

        try {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;
            String requestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", prompt.replace("\"", "\\\"").replace("\n", " "));

            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                // Simple parsing or fallback to body snippet
                return response.body();
            }
        } catch (Exception e) {
            System.err.println("Gemini API call failed: " + e.getMessage());
        }

        return "Namaste! I am currently operating in offline mode. Please verify your Gemini API key.";
    }
}