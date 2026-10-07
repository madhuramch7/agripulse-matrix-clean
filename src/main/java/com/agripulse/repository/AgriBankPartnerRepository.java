package com.agripulse.repository;

import com.agripulse.entity.AgriBankPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgriBankPartnerRepository extends JpaRepository<AgriBankPartner, Long> {
    Optional<AgriBankPartner> findByBankName(String bankName);
}