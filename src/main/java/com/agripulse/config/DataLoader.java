package com.agripulse.config;

import com.agripulse.entity.MarketPriceIndex;
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

    public DataLoader(MarketPriceIndexRepository marketPriceIndexRepository) {
        this.marketPriceIndexRepository = marketPriceIndexRepository;
    }

    @Override
    public void run(String... args) {
        if (marketPriceIndexRepository.count() == 0) {
            loadMarketPrices();
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

    private static String clean(String v) {
        return v == null ? "" : v.trim().replaceAll("^\"|\"$", "").trim();
    }

    private static BigDecimal toDecimal(String v) {
        String c = clean(v).replace(",", "");
        return c.isEmpty() ? BigDecimal.ZERO : new BigDecimal(c);
    }

    private static LocalDate toDate(String v) {
        try {
            return LocalDate.parse(clean(v), DATE_FMT);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }
}
