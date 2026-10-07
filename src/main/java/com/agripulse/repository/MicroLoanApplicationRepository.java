package com.agripulse.repository;

import com.agripulse.entity.MicroLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroLoanApplicationRepository extends JpaRepository<MicroLoanApplication, Long> {
}