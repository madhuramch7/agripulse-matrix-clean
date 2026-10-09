package com.agripulse.config;

import com.agripulse.entity.CropMaster;
import com.agripulse.entity.MarketPriceIndex;
import com.agripulse.repository.CropMasterRepository;
import com.agripulse.repository.MarketPriceIndexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    private final CropMasterRepository cropRepository;
    private final MarketPriceIndexRepository marketPriceRepository;

    public DataLoader(CropMasterRepository cropRepository, MarketPriceIndexRepository marketPriceRepository) {
        this.cropRepository = cropRepository;
        this.marketPriceRepository = marketPriceRepository;
    }

    @Override
    public void run(String... args) {
        loadCropData();
        loadMarketData();
    }

    private void loadCropData() {
        if (cropRepository.count() > 0) return;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/crop_recommendation.csv")))) {
            
            String line;
            br.readLine(); // Skip header
            
            Map<String, List<double[]>> cropStats = new HashMap<>();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 8) continue;
                
                String label = values[7].trim();
                double[] metrics = new double[7];
                for (int i = 0; i < 7; i++) {
                    metrics[i] = Double.parseDouble(values[i].trim());
                }
                
                cropStats.computeIfAbsent(label, k -> new ArrayList<>()).add(metrics);
            }
            
            for (Map.Entry<String, List<double[]>> entry : cropStats.entrySet()) {
                CropMaster cm = new CropMaster();
                cm.setLabel(entry.getKey());
                // Simplified mean mapping for brevity
                cm.setNMean(calculateMean(entry.getValue(), 0));
                cm.setPMean(calculateMean(entry.getValue(), 1));
                cm.setKMean(calculateMean(entry.getValue(), 2));
                cm.setTempMean(calculateMean(entry.getValue(), 3));
                cm.setHumidityMean(calculateMean(entry.getValue(), 4));
                cm.setPhMean(calculateMean(entry.getValue(), 5));
                cm.setRainfallMean(calculateMean(entry.getValue(), 6));
                
                cropRepository.save(cm);
            }
            System.out.println("Crop data loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading crop data: " + e.getMessage());
        }
    }

    private void loadMarketData() {
        if (marketPriceRepository.count() > 0) return;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/market_prices.csv")))) {
            
            String line;
            br.readLine(); // Skip header
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 5) continue;
                
                MarketPriceIndex mpi = new MarketPriceIndex();
                mpi.setState(values[0].trim());
                mpi.setDistrict(values[1].trim());
                mpi.setMarket(values[2].trim());
                mpi.setCommodity(values[3].trim());
                
                try {
                    mpi.setModalPrice(new BigDecimal(values[4].trim()));
                    mpi.setArrivalDate(LocalDate.now());
                    marketPriceRepository.save(mpi);
                } catch (NumberFormatException ignored) {}
            }
            System.out.println("Market prices loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading market prices: " + e.getMessage());
        }
    }

    private double calculateMean(List<double[]> data, int index) {
        return data.stream().mapToDouble(arr -> arr[index]).average().orElse(0.0);
    }
}