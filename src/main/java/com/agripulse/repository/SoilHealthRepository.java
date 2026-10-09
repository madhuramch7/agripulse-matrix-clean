package com.agripulse.repository;

import com.agripulse.entity.SoilHealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SoilHealthRepository extends JpaRepository<SoilHealthRecord, Long> {
    List<SoilHealthRecord> findByLandPlot_Id(Long id);
}