package com.agripulse.repository;

import com.agripulse.entity.CropCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CropCycleRepository extends JpaRepository<CropCycle, Long> {
    List<CropCycle> findByLandPlot_PlotId(Long plotId);
}