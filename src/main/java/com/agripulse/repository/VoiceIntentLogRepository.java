package com.agripulse.repository;

import com.agripulse.entity.VoiceIntentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoiceIntentLogRepository extends JpaRepository<VoiceIntentLog, Long> {
    List<VoiceIntentLog> findByUserAccount_UserId(Long userId);
}