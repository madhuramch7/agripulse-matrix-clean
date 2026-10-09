package com.agripulse.repository;

import com.agripulse.entity.CropRecommendationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRecommendationRepository extends JpaRepository<CropRecommendationRecord, Long> {
}