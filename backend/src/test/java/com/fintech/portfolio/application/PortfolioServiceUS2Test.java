package com.fintech.portfolio.application;

import com.fintech.portfolio.application.port.out.MarketPriceProvider;
import com.fintech.portfolio.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** US2 — Unit-Tests für den Portfoliowert mit deterministischem Stub-Kurs. */
class PortfolioServiceUS2Test {

    private final MarketPriceProvider prices = isin -> switch (isin) {
        case "AAA" -> new BigDecimal("100.00");
        case "BBB" -> new BigDecimal("50.00");
        default -> BigDecimal.ZERO;
    };
    private final PortfolioService service = new PortfolioService(prices);

    @Test
    void leeresPortfolio_hatWertNull() {
        assertEquals(0, service.totalValue().compareTo(new BigDecimal("0.00")));
    }

    @Test
    void gesamtwert_istSummeAusStueckzahlMalMarktkurs() {
        service.addPosition(new Position("AAA", new BigDecimal("10"), new BigDecimal("80.00"))); // 10*100=1000
        service.addPosition(new Position("BBB", new BigDecimal("4"), new BigDecimal("40.00")));   //  4*50= 200
        assertEquals(0, service.totalValue().compareTo(new BigDecimal("1200.00")));
    }

    @Test
    void fraktionaleStueckzahl_wirdKorrektBewertet() {
        service.addPosition(new Position("AAA", new BigDecimal("0.5"), new BigDecimal("90.00"))); // 0.5*100=50
        assertEquals(0, service.totalValue().compareTo(new BigDecimal("50.00")));
    }
}
