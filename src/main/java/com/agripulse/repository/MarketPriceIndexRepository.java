package com.agripulse.repository;

import com.agripulse.entity.MarketPriceIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarketPriceIndexRepository extends JpaRepository<MarketPriceIndex, Long> {
    List<MarketPriceIndex> findByCommodityNameAndMarketMandi(String commodityName, String marketMandi);
}