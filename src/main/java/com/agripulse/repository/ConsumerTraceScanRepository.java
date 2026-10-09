package com.agripulse.repository;

import com.agripulse.entity.ConsumerTraceScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConsumerTraceScanRepository extends JpaRepository<ConsumerTraceScan, Long> {
    List<ConsumerTraceScan> findByHarvestBatch_BatchId(Long batchId);
}