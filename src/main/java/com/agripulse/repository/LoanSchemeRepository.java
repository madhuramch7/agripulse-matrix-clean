package com.agripulse.repository;

import com.agripulse.entity.LoanSchemeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanSchemeRepository extends JpaRepository<LoanSchemeMaster, Long> {
    List<LoanSchemeMaster> findByMinApciScoreLessThanEqual(Integer apciScore);
}