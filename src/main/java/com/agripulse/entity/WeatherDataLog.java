package com.agripulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherLogId;

    private String regionCode;
    private Double rainfallMm;
    private Double tempCelsius;
    private Double humidityPct;
    private Boolean isExtremeFlag = false;
    private LocalDateTime loggedAt = LocalDateTime.now();
}