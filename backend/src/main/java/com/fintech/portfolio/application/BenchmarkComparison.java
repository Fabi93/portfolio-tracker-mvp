package com.fintech.portfolio.application;

import java.math.BigDecimal;

/**
 * Ergebnis des Benchmark-Vergleichs (Ausbaustufe 2). Alle Werte in Prozent(-punkten).
 *
 * @param benchmarkId         verwendete Benchmark-ID
 * @param portfolioReturnPct  prozentuale Rendite des Portfolios
 * @param benchmarkReturnPct  prozentuale Rendite der Benchmark
 * @param outperformancePct   Portfolio − Benchmark (positiv = Outperformance)
 */
public record BenchmarkComparison(
        String benchmarkId,
        BigDecimal portfolioReturnPct,
        BigDecimal benchmarkReturnPct,
        BigDecimal outperformancePct) {
}
