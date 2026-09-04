package com.fintech.portfolio.application.port.out;

import java.math.BigDecimal;

/**
 * Ausgangs-Port (Clean Architecture): liefert den aktuellen Marktkurs zu einer ISIN in EUR.
 *
 * <p>Im MVP durch eine Simulation implementiert (siehe
 * {@code adapter.out.price.SimulatedMarketPriceProvider}). Später austauschbar gegen
 * eine echte Kursquelle, ohne die Geschäftslogik zu ändern.
 */
public interface MarketPriceProvider {

    /**
     * @param isin Wertpapier-Kennnummer
     * @return aktueller Kurs pro Stück in EUR
     */
    BigDecimal currentPriceOf(String isin);
}
