package com.fintech.portfolio.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** US1 — Unit-Tests der Positions-Invarianten (Happy + Error Cases). */
class PositionTest {

    @Test
    void gueltigePosition_berechnetHistorischenKaufwert() {
        Position position = new Position("IE00B4L5Y983", new BigDecimal("10"), new BigDecimal("90.00"));
        assertEquals(0, position.historicalCost().compareTo(new BigDecimal("900.00")));
    }

    @Test
    void leereIsin_wirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position("  ", new BigDecimal("1"), new BigDecimal("1")));
    }

    @Test
    void nichtPositiveStueckzahl_wirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position("IE00B4L5Y983", new BigDecimal("0"), new BigDecimal("1")));
    }

    @Test
    void negativerKaufkurs_wirdAbgelehnt() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position("IE00B4L5Y983", new BigDecimal("1"), new BigDecimal("-1")));
    }
}
