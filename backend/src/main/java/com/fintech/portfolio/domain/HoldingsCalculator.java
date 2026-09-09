package com.fintech.portfolio.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Leitet aus einer Liste von Transaktionen dynamisch den aktuellen Bestand (Quantity) und den
 * durchschnittlichen Einstiegskurs (Average Buy-In) je ISIN ab (Ausbaustufe 1).
 *
 * <p>Regeln: Transaktionen werden chronologisch verarbeitet. BUY erhöht Menge und Kostenbasis;
 * SELL reduziert die Menge zum aktuellen Durchschnittskurs (der Ø-Einstieg bleibt dabei
 * unverändert). Ein Verkauf über den Bestand hinaus ist ein Fehler. Nur Bestände mit Menge &gt; 0
 * werden zurückgegeben.
 */
public final class HoldingsCalculator {

    private static final int MONEY_SCALE = 2;
    private static final int INTERNAL_SCALE = 10;

    private HoldingsCalculator() {
    }

    public static List<Holding> from(List<Transaction> transactions) {
        Map<String, Accumulator> byIsin = new LinkedHashMap<>();
        transactions.stream()
                .sorted(Comparator.comparing(Transaction::date))
                .forEach(t -> byIsin.computeIfAbsent(t.isin(), k -> new Accumulator()).apply(t));

        List<Holding> holdings = new ArrayList<>();
        byIsin.forEach((isin, acc) -> {
            if (acc.quantity.signum() > 0) {
                holdings.add(new Holding(isin, acc.quantity, acc.reportedAverageBuyIn()));
            }
        });
        return List.copyOf(holdings);
    }

    private static final class Accumulator {
        private BigDecimal quantity = BigDecimal.ZERO;
        private BigDecimal totalCost = BigDecimal.ZERO;

        void apply(Transaction transaction) {
            switch (transaction.type()) {
                case BUY -> {
                    quantity = quantity.add(transaction.quantity());
                    totalCost = totalCost.add(transaction.quantity().multiply(transaction.price()));
                }
                case SELL -> {
                    if (transaction.quantity().compareTo(quantity) > 0) {
                        throw new IllegalArgumentException(
                                "Verkauf überschreitet den Bestand für " + transaction.isin());
                    }
                    BigDecimal averageBuyIn = internalAverageBuyIn();
                    totalCost = totalCost.subtract(transaction.quantity().multiply(averageBuyIn));
                    quantity = quantity.subtract(transaction.quantity());
                    if (quantity.signum() == 0) {
                        totalCost = BigDecimal.ZERO;
                    }
                }
            }
        }

        private BigDecimal internalAverageBuyIn() {
            return quantity.signum() == 0
                    ? BigDecimal.ZERO
                    : totalCost.divide(quantity, INTERNAL_SCALE, RoundingMode.HALF_EVEN);
        }

        BigDecimal reportedAverageBuyIn() {
            return quantity.signum() == 0
                    ? BigDecimal.ZERO.setScale(MONEY_SCALE, RoundingMode.HALF_EVEN)
                    : totalCost.divide(quantity, MONEY_SCALE, RoundingMode.HALF_EVEN);
        }
    }
}
