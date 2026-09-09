package com.fintech.portfolio.domain;

import java.math.BigDecimal;

/**
 * Ein aus Transaktionen abgeleiteter Bestand (Ausbaustufe 1).
 *
 * @param isin          Wertpapier-Kennnummer
 * @param quantity      aktueller Bestand (> 0)
 * @param averageBuyIn  durchschnittlicher Einstiegskurs pro Stück in EUR
 */
public record Holding(String isin, BigDecimal quantity, BigDecimal averageBuyIn) {
}
