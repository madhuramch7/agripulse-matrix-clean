package com.agripulse.repository;

import com.agripulse.entity.MicroLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MicroLoanApplicationRepository extends JpaRepository<MicroLoanApplication, Long> {
    List<MicroLoanApplication> findByFarmerProfile_FarmerId(Long farmerId);
}