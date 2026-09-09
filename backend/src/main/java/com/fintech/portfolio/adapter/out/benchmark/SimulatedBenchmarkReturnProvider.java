package com.fintech.portfolio.adapter.out.benchmark;

import com.fintech.portfolio.application.port.out.BenchmarkReturnProvider;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * Simulierte Benchmark-Renditen für das MVP (feste Prozentwerte je ID). In einer echten
 * Ausbaustufe durch eine Kurs-/Indexquelle mit Zeitraumbezug ersetzbar — der Port bleibt gleich.
 */
@ApplicationScoped
public class SimulatedBenchmarkReturnProvider implements BenchmarkReturnProvider {

    private static final Map<String, BigDecimal> RETURNS = Map.of(
            "MSCI_WORLD", new BigDecimal("8.00"),
            "SP500", new BigDecimal("10.00"),
            "MSCI_EM", new BigDecimal("5.00")
    );

    @Override
    public Optional<BigDecimal> returnPercentFor(String benchmarkId) {
        return Optional.ofNullable(RETURNS.get(benchmarkId));
    }
}
