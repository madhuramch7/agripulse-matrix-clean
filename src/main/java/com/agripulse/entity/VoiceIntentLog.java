package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "voice_intent_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceIntentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount userAccount;

    private String rawAudioText;
    private String detectedLanguage;
    private String parsedIntent; // e.g. APPLY_LOAN, CHECK_APCI, WEATHER_QUERY
    private LocalDateTime loggedAt = LocalDateTime.now();
}