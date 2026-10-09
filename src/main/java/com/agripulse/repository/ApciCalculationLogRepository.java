package com.agripulse.repository;

import com.agripulse.entity.ApciCalculationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApciCalculationLogRepository extends JpaRepository<ApciCalculationLog, Long> {
    List<ApciCalculationLog> findByFarmerId(Long farmerId);
}