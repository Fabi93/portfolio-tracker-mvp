package com.fintech.portfolio.application;

import com.fintech.portfolio.application.port.out.BenchmarkReturnProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Benchmark-Vergleich (Ausbaustufe 2): stellt die prozentuale Portfolio-Rendite der Rendite
 * einer Benchmark gegenüber und weist die Out-/Underperformance aus.
 *
 * <p>Basis der Portfolio-Rendite ist das positionsbasierte Portfolio (US1–US3).
 */
@ApplicationScoped
public class BenchmarkService {

    private static final int PERCENT_SCALE = 2;
    private static final int INTERNAL_SCALE = 8;
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    private final PortfolioService portfolioService;
    private final BenchmarkReturnProvider benchmarkReturnProvider;

    @Inject
    public BenchmarkService(PortfolioService portfolioService, BenchmarkReturnProvider benchmarkReturnProvider) {
        this.portfolioService = portfolioService;
        this.benchmarkReturnProvider = benchmarkReturnProvider;
    }

    public BenchmarkComparison compareWith(String benchmarkId) {
        BigDecimal benchmarkReturn = benchmarkReturnProvider.returnPercentFor(benchmarkId)
                .orElseThrow(() -> new IllegalArgumentException("Unbekannte Benchmark-ID: " + benchmarkId));
        BigDecimal portfolioReturn = portfolioReturnPercent();
        BigDecimal outperformance = portfolioReturn.subtract(benchmarkReturn)
                .setScale(PERCENT_SCALE, RoundingMode.HALF_EVEN);
        return new BenchmarkComparison(benchmarkId, portfolioReturn, benchmarkReturn, outperformance);
    }

    /** (Gesamtwert − historischer Kaufwert) / historischer Kaufwert × 100; Kaufwert 0 ⇒ 0. */
    private BigDecimal portfolioReturnPercent() {
        BigDecimal cost = portfolioService.historicalCost();
        if (cost.signum() == 0) {
            return BigDecimal.ZERO.setScale(PERCENT_SCALE, RoundingMode.HALF_EVEN);
        }
        BigDecimal gain = portfolioService.totalValue().subtract(cost);
        return gain.divide(cost, INTERNAL_SCALE, RoundingMode.HALF_EVEN)
                .multiply(HUNDRED)
                .setScale(PERCENT_SCALE, RoundingMode.HALF_EVEN);
    }
}
