package com.agripulse.repository;

import com.agripulse.entity.LoanSchemeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanSchemeRepository extends JpaRepository<LoanSchemeMaster, Long> {
}