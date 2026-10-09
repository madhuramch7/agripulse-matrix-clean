package com.agripulse.repository;

import com.agripulse.entity.HarvestBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HarvestBatchRepository extends JpaRepository<HarvestBatch, Long> {
    List<HarvestBatch> findByCropCycle_LandPlot_Id(Long plotId);
}