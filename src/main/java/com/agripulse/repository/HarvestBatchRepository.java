package com.agripulse.repository;

import com.agripulse.entity.HarvestBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HarvestBatchRepository extends JpaRepository<HarvestBatch, Long> {
    Optional<HarvestBatch> findByQrCodeHash(String qrCodeHash);
}