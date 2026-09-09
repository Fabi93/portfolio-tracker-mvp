package com.fintech.portfolio.application;

import com.fintech.portfolio.application.port.out.BenchmarkReturnProvider;
import com.fintech.portfolio.application.port.out.MarketPriceProvider;
import com.fintech.portfolio.domain.Position;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Ausbaustufe 2 — Unit-Tests für Portfolio-Rendite, Benchmark-Vergleich und Fehlerfälle. */
class BenchmarkServiceTest {

    private final MarketPriceProvider prices = isin -> new BigDecimal("102.50");
    private final BenchmarkReturnProvider benchmarks = id ->
            "MSCI_WORLD".equals(id) ? Optional.of(new BigDecimal("8.00")) : Optional.empty();

    private final PortfolioService portfolio = new PortfolioService(prices);
    private final BenchmarkService service = new BenchmarkService(portfolio, benchmarks);

    @Test
    void outperformance_wirdKorrektBerechnet() {
        // Kaufwert 900 (10*90), Wert 1025 (10*102.50) -> Rendite 13.89 %
        portfolio.addPosition(new Position("X", new BigDecimal("10"), new BigDecimal("90.00")));

        BenchmarkComparison result = service.compareWith("MSCI_WORLD");

        assertEquals(0, result.portfolioReturnPct().compareTo(new BigDecimal("13.89")));
        assertEquals(0, result.benchmarkReturnPct().compareTo(new BigDecimal("8.00")));
        assertEquals(0, result.outperformancePct().compareTo(new BigDecimal("5.89")));
    }

    @Test
    void leeresPortfolio_hatRenditeNull_underperformt() {
        BenchmarkComparison result = service.compareWith("MSCI_WORLD");

        assertEquals(0, result.portfolioReturnPct().compareTo(new BigDecimal("0.00")));
        assertEquals(0, result.outperformancePct().compareTo(new BigDecimal("-8.00")));
    }

    @Test
    void unbekannteBenchmark_wirftFehler() {
        assertThrows(IllegalArgumentException.class, () -> service.compareWith("GIBT_ES_NICHT"));
    }
}
