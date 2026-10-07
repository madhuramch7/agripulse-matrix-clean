package com.agripulse.repository;

import com.agripulse.entity.MarketPriceIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketPriceIndexRepository extends JpaRepository<MarketPriceIndex, Long> {
}