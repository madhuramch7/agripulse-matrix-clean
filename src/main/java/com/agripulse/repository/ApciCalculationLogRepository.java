package com.agripulse.repository;

import com.agripulse.entity.ApciCalculationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApciCalculationLogRepository extends JpaRepository<ApciCalculationLog, Long> {
    List<ApciCalculationLog> findByFarmerProfile_FarmerId(Long farmerId);
}