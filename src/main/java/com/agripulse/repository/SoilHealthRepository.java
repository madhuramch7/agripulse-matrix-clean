package com.agripulse.repository;

import com.agripulse.entity.SoilHealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoilHealthRepository extends JpaRepository<SoilHealthRecord, Long> {
    List<SoilHealthRecord> findByLandPlot_PlotId(Long plotId);
}