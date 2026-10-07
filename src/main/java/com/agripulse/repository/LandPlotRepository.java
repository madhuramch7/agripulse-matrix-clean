package com.agripulse.repository;

import com.agripulse.entity.LandPlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LandPlotRepository extends JpaRepository<LandPlot, Long> {
    List<LandPlot> findByFarmerProfile_FarmerId(Long farmerId);
}