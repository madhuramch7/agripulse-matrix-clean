package com.agripulse.repository;

import com.agripulse.entity.WeatherDataLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherDataLog, Long> {
    List<WeatherDataLog> findByRegionCode(String regionCode);
}