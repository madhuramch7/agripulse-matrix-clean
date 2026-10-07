package com.agripulse.repository;

import com.agripulse.entity.ConsumerTraceScan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsumerTraceScanRepository extends JpaRepository<ConsumerTraceScan, Long> {
    List<ConsumerTraceScan> findByHarvestBatch_BatchId(Long batchId);
}