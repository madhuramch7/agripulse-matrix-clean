package com.agripulse.config;

import com.agripulse.entity.MarketPriceIndex;
import com.agripulse.repository.MarketPriceIndexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final MarketPriceIndexRepository marketPriceIndexRepository;

    public DataLoader(MarketPriceIndexRepository marketPriceIndexRepository) {
        this.marketPriceIndexRepository = marketPriceIndexRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (marketPriceIndexRepository.count() == 0) {
            loadMarketPrices();
        }
    }

    private void loadMarketPrices() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/market_prices.csv"), StandardCharsets.UTF_8))) {
            
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length >= 9) {
                    MarketPriceIndex mpi = new MarketPriceIndex();
                    mpi.setState(data[0].trim());
                    mpi.setDistrict(data[1].trim());
                    mpi.setMarket(data[2].trim());
                    mpi.setCommodity(data[3].trim());
                    
                    // Modal price is the final column (index 8)
                    String priceStr = data[data.length - 1].trim();
                    mpi.setModalPrice(new BigDecimal(priceStr.isEmpty() ? "0.00" : priceStr));
                    mpi.setArrivalDate(LocalDate.now());
                    
                    marketPriceIndexRepository.save(mpi);
                }
            }
            System.out.println("Successfully loaded market prices into MarketPriceIndex.");
        } catch (Exception e) {
            System.err.println("Could not load market_prices.csv: " + e.getMessage());
        }
    }
}