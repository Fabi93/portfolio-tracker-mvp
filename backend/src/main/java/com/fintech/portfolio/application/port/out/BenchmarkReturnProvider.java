package com.fintech.portfolio.application.port.out;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Ausgangs-Port (Ausbaustufe 2): liefert die prozentuale Rendite einer Benchmark je ID.
 * Im MVP simuliert; leeres Optional = unbekannte Benchmark-ID.
 */
public interface BenchmarkReturnProvider {

    /**
     * @param benchmarkId z. B. "MSCI_WORLD"
     * @return prozentuale Rendite der Benchmark, oder leer bei unbekannter ID
     */
    Optional<BigDecimal> returnPercentFor(String benchmarkId);
}
