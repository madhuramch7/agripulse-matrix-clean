package com.agripulse.repository;

import com.agripulse.entity.DisasterReliefClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DisasterReliefClaimRepository extends JpaRepository<DisasterReliefClaim, Long> {
    List<DisasterReliefClaim> findByLandPlot_PlotId(Long plotId);
}