package com.fintech.portfolio.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Ausbaustufe 1 — Unit-Tests: Transaktionen → Bestand & Ø-Einstiegskurs. */
class HoldingsCalculatorTest {

    private static LocalDate day(int d) {
        return LocalDate.of(2026, 1, d);
    }

    private static Transaction buy(String isin, int d, String qty, String price) {
        return new Transaction(TransactionType.BUY, isin, day(d), new BigDecimal(qty), new BigDecimal(price));
    }

    private static Transaction sell(String isin, int d, String qty, String price) {
        return new Transaction(TransactionType.SELL, isin, day(d), new BigDecimal(qty), new BigDecimal(price));
    }

    @Test
    void einzelkauf_setztMengeUndDurchschnitt() {
        List<Holding> holdings = HoldingsCalculator.from(List.of(buy("X", 1, "10", "90.00")));
        assertEquals(1, holdings.size());
        assertEquals("X", holdings.get(0).isin());
        assertEquals(0, holdings.get(0).quantity().compareTo(new BigDecimal("10")));
        assertEquals(0, holdings.get(0).averageBuyIn().compareTo(new BigDecimal("90.00")));
    }

    @Test
    void mehrereKaeufe_ergebenKostengewichtetenDurchschnitt() {
        List<Holding> holdings = HoldingsCalculator.from(List.of(
                buy("X", 1, "10", "90.00"),
                buy("X", 2, "10", "110.00")));
        assertEquals(0, holdings.get(0).quantity().compareTo(new BigDecimal("20")));
        assertEquals(0, holdings.get(0).averageBuyIn().compareTo(new BigDecimal("100.00")));
    }

    @Test
    void verkauf_reduziertMenge_durchschnittBleibt() {
        List<Holding> holdings = HoldingsCalculator.from(List.of(
                buy("X", 1, "10", "90.00"),
                sell("X", 2, "4", "120.00")));
        assertEquals(0, holdings.get(0).quantity().compareTo(new BigDecimal("6")));
        assertEquals(0, holdings.get(0).averageBuyIn().compareTo(new BigDecimal("90.00")));
    }

    @Test
    void vollstaendigerVerkauf_entferntBestand() {
        List<Holding> holdings = HoldingsCalculator.from(List.of(
                buy("X", 1, "10", "90.00"),
                sell("X", 2, "10", "120.00")));
        assertTrue(holdings.isEmpty());
    }

    @Test
    void verkaufUeberBestand_wirftFehler() {
        List<Transaction> txs = List.of(buy("X", 1, "10", "90.00"), sell("X", 2, "11", "120.00"));
        assertThrows(IllegalArgumentException.class, () -> HoldingsCalculator.from(txs));
    }

    @Test
    void transaktionen_werdenChronologischVerarbeitet() {
        // Verkauf mit früherem Datum als der Kauf -> würde bei falscher Reihenfolge fehlschlagen.
        List<Holding> holdings = HoldingsCalculator.from(List.of(
                buy("X", 5, "10", "90.00"),
                buy("X", 1, "10", "110.00")));
        assertEquals(0, holdings.get(0).quantity().compareTo(new BigDecimal("20")));
        assertEquals(0, holdings.get(0).averageBuyIn().compareTo(new BigDecimal("100.00")));
    }

    @Test
    void mehrereIsins_werdenGetrenntGefuehrt() {
        List<Holding> holdings = HoldingsCalculator.from(List.of(
                buy("X", 1, "10", "90.00"),
                buy("Y", 1, "5", "40.00")));
        assertEquals(2, holdings.size());
    }
}
