package com.agripulse.dto;

import lombok.Data;

@Data
public class OnboardingRequest {
    private Double farmSize;
    private String state;
    private String district;
    private String primaryCrop;
    private String soilType;
    private String irrigationType;
}