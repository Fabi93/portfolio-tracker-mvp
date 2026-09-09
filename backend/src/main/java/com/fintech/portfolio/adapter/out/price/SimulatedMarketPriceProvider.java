package com.fintech.portfolio.adapter.out.price;

import com.fintech.portfolio.application.port.out.MarketPriceProvider;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Simulierte Kursquelle für das MVP (PRD: „simulierte Marktkurse").
 *
 * <p>Deterministisch: bekannte ISINs haben feste Beispielkurse; für unbekannte ISINs wird ein
 * stabiler Kurs aus dem Hash abgeleitet, damit das Verhalten reproduzierbar bleibt. In einer
 * echten Ausbaustufe wird dieser Adapter gegen eine reale Kursquelle getauscht — die
 * Geschäftslogik bleibt unverändert.
 */
@ApplicationScoped
public class SimulatedMarketPriceProvider implements MarketPriceProvider {

    private static final Map<String, BigDecimal> KNOWN_PRICES = Map.of(
            "IE00B4L5Y983", new BigDecimal("102.50"), // iShares Core MSCI World
            "US0378331005", new BigDecimal("210.75"), // Apple
            "US5949181045", new BigDecimal("415.20")  // Microsoft
    );

    @Override
    public BigDecimal currentPriceOf(String isin) {
        BigDecimal known = KNOWN_PRICES.get(isin);
        if (known != null) {
            return known;
        }
        int base = Math.floorMod(isin.hashCode(), 50_000);
        return BigDecimal.valueOf(1000L + base, 2).setScale(2, RoundingMode.HALF_EVEN);
    }
}
