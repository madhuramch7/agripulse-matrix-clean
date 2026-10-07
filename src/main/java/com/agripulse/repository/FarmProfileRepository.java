package com.agripulse.repository;

import com.agripulse.entity.FarmProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmProfileRepository extends JpaRepository<FarmProfile, Long> {
}