package com.fintech.portfolio.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** US1 — Unit-Tests der Positions-Invarianten (Happy + Error Cases). */
class PositionTest {

    private static final String ISIN = "IE00B4L5Y983";
    private static final BigDecimal ONE = new BigDecimal("1");
    private static final BigDecimal ZERO = new BigDecimal("0");
    private static final BigDecimal NEGATIVE = new BigDecimal("-1");

    @Test
    void gueltigePosition_berechnetHistorischenKaufwert() {
        Position position = new Position(ISIN, new BigDecimal("10"), new BigDecimal("90.00"));
        assertEquals(0, position.historicalCost().compareTo(new BigDecimal("900.00")));
    }

    @Test
    void leereIsin_wirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class, () -> new Position("  ", ONE, ONE));
    }

    @Test
    void nichtPositiveStueckzahl_wirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class, () -> new Position(ISIN, ZERO, ONE));
    }

    @Test
    void negativerKaufkurs_wirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class, () -> new Position(ISIN, ONE, NEGATIVE));
    }
}
