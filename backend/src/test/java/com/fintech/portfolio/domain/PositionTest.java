package com.fintech.portfolio.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit-Tests der Positions-Validierung (US1, Happy Path + Error Cases).
 * Diese Tests sind GRÜN und bilden die Baseline.
 */
class PositionTest {

    @Test
    void erstellt_gueltige_position_und_berechnet_historischen_kaufwert() {
        Position position = new Position("IE00B4L5Y983", new BigDecimal("10"), new BigDecimal("90.00"));

        assertThat(position.historicalCost()).isEqualByComparingTo("900.00");
    }

    @Test
    void lehnt_leere_isin_ab() {
        assertThatThrownBy(() -> new Position("  ", new BigDecimal("1"), new BigDecimal("1")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lehnt_nicht_positive_stueckzahl_ab() {
        assertThatThrownBy(() -> new Position("IE00B4L5Y983", new BigDecimal("0"), new BigDecimal("1")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void lehnt_negativen_kaufkurs_ab() {
        assertThatThrownBy(() -> new Position("IE00B4L5Y983", new BigDecimal("1"), new BigDecimal("-1")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
