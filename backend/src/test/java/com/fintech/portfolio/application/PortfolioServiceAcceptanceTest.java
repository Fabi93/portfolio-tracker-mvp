package com.fintech.portfolio.application;

import com.fintech.portfolio.application.port.out.MarketPriceProvider;
import com.fintech.portfolio.domain.Position;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Acceptance-Tests für US2 (Portfoliowert) und US3 (Gewinn/Verlust).
 *
 * <p><b>Test-First:</b> Diese Tests sind das ausführbare Abbild der Akzeptanzkriterien
 * (siehe docs/refinement/acceptance-tests.md) und aktuell {@code @Disabled}. Im Workshop
 * werden sie zu Beginn der Delivery aktiviert (rot) und die Logik in
 * {@link PortfolioService} anschließend dagegen implementiert (grün).
 *
 * <p>Feste Testkurse über einen Stub-{@link MarketPriceProvider}, damit die Erwartungswerte
 * deterministisch sind.
 */
@Disabled("Test-First: zu Beginn der Delivery aktivieren, dann PortfolioService implementieren")
class PortfolioServiceAcceptanceTest {

    private final MarketPriceProvider stubPrices = isin -> Map.of(
            "AAA", new BigDecimal("100.00"),
            "BBB", new BigDecimal("50.00")
    ).getOrDefault(isin, BigDecimal.ZERO);

    private final PortfolioService service = new PortfolioService(stubPrices);

    @Test
    void us2_leeres_portfolio_hat_wert_null() {
        // Given: keine Positionen
        // When/Then
        assertThat(service.totalValue()).isEqualByComparingTo("0");
    }

    @Test
    void us2_gesamtwert_ist_summe_aus_stueckzahl_mal_marktkurs() {
        // Given
        service.addPosition(new Position("AAA", new BigDecimal("10"), new BigDecimal("80.00"))); // 10 * 100 = 1000
        service.addPosition(new Position("BBB", new BigDecimal("4"), new BigDecimal("40.00")));   //  4 *  50 =  200
        // When/Then
        assertThat(service.totalValue()).isEqualByComparingTo("1200.00");
    }

    @Test
    void us3_gewinn_wird_positiv_ausgewiesen() {
        // Given: Kaufwert 10*80 = 800, aktueller Wert 10*100 = 1000
        service.addPosition(new Position("AAA", new BigDecimal("10"), new BigDecimal("80.00")));
        // When/Then: 1000 - 800 = 200
        assertThat(service.absoluteProfitLoss()).isEqualByComparingTo("200.00");
    }

    @Test
    void us3_verlust_wird_negativ_ausgewiesen() {
        // Given: Kaufwert 4*60 = 240, aktueller Wert 4*50 = 200
        service.addPosition(new Position("BBB", new BigDecimal("4"), new BigDecimal("60.00")));
        // When/Then: 200 - 240 = -40
        assertThat(service.absoluteProfitLoss()).isEqualByComparingTo("-40.00");
    }
}
