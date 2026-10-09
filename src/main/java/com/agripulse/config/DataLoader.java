package com.agripulse.config;

import com.agripulse.entity.CropRecommendationRecord;
import com.agripulse.entity.MarketPriceIndex;
import com.agripulse.repository.CropRecommendationRepository;
import com.agripulse.repository.MarketPriceIndexRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final MarketPriceIndexRepository marketPriceIndexRepository;
    private final CropRecommendationRepository cropRecommendationRepository;

    public DataLoader(MarketPriceIndexRepository marketPriceIndexRepository,
                      CropRecommendationRepository cropRecommendationRepository) {
        this.marketPriceIndexRepository = marketPriceIndexRepository;
        this.cropRecommendationRepository = cropRecommendationRepository;
    }

    @Override
    public void run(String... args) {
        if (marketPriceIndexRepository.count() == 0) {
            loadMarketPrices();
        }
        if (cropRecommendationRepository.count() == 0) {
            loadCropRecommendations();
        }
    }

    private void loadMarketPrices() {
        InputStream in = getClass().getResourceAsStream("/data/market_prices.csv");
        if (in == null) {
            System.err.println("market_prices.csv not found on classpath under /data/");
            return;
        }
        List<MarketPriceIndex> batch = new ArrayList<>();
        int loaded = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                try {
                    String[] d = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if (d.length < 9) continue;
                    MarketPriceIndex m = new MarketPriceIndex();
                    m.setState(clean(d[0]));
                    m.setDistrict(clean(d[1]));
                    m.setMarket(clean(d[2]));
                    m.setCommodity(clean(d[3]));
                    m.setModalPrice(toDecimal(d[8]));
                    m.setArrivalDate(toDate(d[5]));
                    batch.add(m);
                    if (batch.size() >= 500) {
                        marketPriceIndexRepository.saveAll(batch);
                        loaded += batch.size();
                        batch.clear();
                    }
                } catch (Exception rowError) {
                    // skip malformed row
                }
            }
            if (!batch.isEmpty()) {
                marketPriceIndexRepository.saveAll(batch);
                loaded += batch.size();
            }
            System.out.println("Loaded " + loaded + " market price rows.");
        } catch (Exception e) {
            System.err.println("Could not load market_prices.csv: " + e.getMessage());
        }
    }

    private void loadCropRecommendations() {
        InputStream in = getClass().getResourceAsStream("/data/crop_recommendation.csv");
        if (in == null) {
            System.err.println("crop_recommendation.csv not found on classpath under /data/");
            return;
        }
        List<CropRecommendationRecord> batch = new ArrayList<>();
        int loaded = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                try {
                    String[] d = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                    if (d.length < 8) continue;
                    CropRecommendationRecord c = new CropRecommendationRecord();
                    c.setNitrogen(toDouble(d[0]));
                    c.setPhosphorus(toDouble(d[1]));
                    c.setPotassium(toDouble(d[2]));
                    c.setTemperature(toDouble(d[3]));
                    c.setHumidity(toDouble(d[4]));
                    c.setPh(toDouble(d[5]));
                    c.setRainfall(toDouble(d[6]));
                    c.setLabel(clean(d[7]));
                    batch.add(c);
                    if (batch.size() >= 500) {
                        cropRecommendationRepository.saveAll(batch);
                        loaded += batch.size();
                        batch.clear();
                    }
                } catch (Exception rowError) {
                    // skip malformed row
                }
            }
            if (!batch.isEmpty()) {
                cropRecommendationRepository.saveAll(batch);
                loaded += batch.size();
            }
            System.out.println("Loaded " + loaded + " crop recommendation rows.");
        } catch (Exception e) {
            System.err.println("Could not load crop_recommendation.csv: " + e.getMessage());
        }
    }

    private static String clean(String v) {
        return v == null ? "" : v.trim().replaceAll("^\"|\"$", "").trim();
    }

    private static BigDecimal toDecimal(String v) {
        String c = clean(v).replace(",", "");
        return c.isEmpty() ? BigDecimal.ZERO : new BigDecimal(c);
    }

    private static Double toDouble(String v) {
        String c = clean(v).replace(",", "");
        return c.isEmpty() ? 0.0 : Double.parseDouble(c);
    }

    private static LocalDate toDate(String v) {
        try {
            return LocalDate.parse(clean(v), DATE_FMT);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}