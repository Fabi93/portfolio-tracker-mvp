package com.fintech.portfolio.application;

import com.fintech.portfolio.application.port.out.MarketPriceProvider;
import com.fintech.portfolio.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** US3 — Unit-Tests für den absoluten Gewinn/Verlust mit deterministischem Stub-Kurs. */
class PortfolioServiceUS3Test {

    private final MarketPriceProvider prices = isin -> switch (isin) {
        case "AAA" -> new BigDecimal("100.00");
        case "BBB" -> new BigDecimal("50.00");
        default -> BigDecimal.ZERO;
    };
    private final PortfolioService service = new PortfolioService(prices);

    @Test
    void leeresPortfolio_hatGuV_null() {
        assertEquals(0, service.absoluteProfitLoss().compareTo(new BigDecimal("0.00")));
    }

    @Test
    void gewinn_wirdPositivAusgewiesen() {
        service.addPosition(new Position("AAA", new BigDecimal("10"), new BigDecimal("80.00"))); // Wert 1000, Kosten 800
        assertEquals(0, service.absoluteProfitLoss().compareTo(new BigDecimal("200.00")));
    }

    @Test
    void verlust_wirdNegativAusgewiesen() {
        service.addPosition(new Position("BBB", new BigDecimal("4"), new BigDecimal("60.00"))); // Wert 200, Kosten 240
        assertEquals(0, service.absoluteProfitLoss().compareTo(new BigDecimal("-40.00")));
    }

    @Test
    void kursGleichKaufkurs_ergibtNull() {
        service.addPosition(new Position("AAA", new BigDecimal("10"), new BigDecimal("100.00"))); // Wert 1000, Kosten 1000
        assertEquals(0, service.absoluteProfitLoss().compareTo(new BigDecimal("0.00")));
    }
}
