package com.agripulse.config;

import com.agripulse.entity.MarketPrice;
import com.agripulse.repository.MarketPriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class DataLoader implements CommandLineRunner {

    private final MarketPriceRepository repository;

    public DataLoader(MarketPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) return; // Skip if already loaded

        InputStream inputStream = getClass().getResourceAsStream("/data/market_prices.csv");
        if (inputStream == null) {
            System.out.println("⚠️ CSV File not found in /data/market_prices.csv");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            reader.readLine(); // Skip header row

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (data.length >= 9) {
                    MarketPrice item = new MarketPrice();
                    item.setState(clean(data[0]));
                    item.setDistrict(clean(data[1]));
                    item.setMarket(clean(data[2]));
                    item.setCommodity(clean(data[3]));
                    item.setVariety(clean(data[4]));
                    item.setArrivalDate(clean(data[5]));
                    item.setMinPrice(parseDouble(clean(data[6])));
                    item.setMaxPrice(parseDouble(clean(data[7])));
                    item.setModalPrice(parseDouble(clean(data[8])));
                    repository.save(item);
                }
            }
            System.out.println("✅ AgriPulse Database: Market price seed data successfully loaded!");
        }
    }

    private String clean(String input) {
        return input == null ? "" : input.replace("\"", "").trim();
    }

    private Double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            return 0.0;
        }
    }
}