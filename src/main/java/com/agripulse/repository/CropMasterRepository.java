package com.agripulse.repository;

import com.agripulse.entity.CropMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropMasterRepository extends JpaRepository<CropMaster, Long> {
}