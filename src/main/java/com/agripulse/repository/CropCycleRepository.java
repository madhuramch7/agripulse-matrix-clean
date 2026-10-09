package com.agripulse.repository;

import com.agripulse.entity.CropCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CropCycleRepository extends JpaRepository<CropCycle, Long> {
    // The underscore safely tells Spring to look for the 'id' field inside the 'landPlot' object
    List<CropCycle> findByLandPlot_Id(Long id);
}