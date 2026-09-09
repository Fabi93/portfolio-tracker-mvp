package com.fintech.portfolio.adapter.in.web.dto;

import com.fintech.portfolio.application.BenchmarkComparison;

import java.math.BigDecimal;

/** Response-DTO für den Benchmark-Vergleich (Ausbaustufe 2). */
public record BenchmarkResponse(
        String benchmarkId,
        BigDecimal portfolioReturnPct,
        BigDecimal benchmarkReturnPct,
        BigDecimal outperformancePct) {

    public static BenchmarkResponse from(BenchmarkComparison comparison) {
        return new BenchmarkResponse(
                comparison.benchmarkId(),
                comparison.portfolioReturnPct(),
                comparison.benchmarkReturnPct(),
                comparison.outperformancePct());
    }
}
