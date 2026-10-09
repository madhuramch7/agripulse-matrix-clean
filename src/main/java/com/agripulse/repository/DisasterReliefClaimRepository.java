package com.agripulse.repository;

import com.agripulse.entity.DisasterReliefClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisasterReliefClaimRepository extends JpaRepository<DisasterReliefClaim, Long> {
    List<DisasterReliefClaim> findByLandPlot_Id(Long id);
}