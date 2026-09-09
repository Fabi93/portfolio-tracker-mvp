package com.fintech.portfolio.domain;

import java.math.BigDecimal;

/**
 * Eine bestandsbasierte Position im Portfolio (US1).
 *
 * <p>Wertobjekt. Geldbeträge in EUR und als {@link BigDecimal} (niemals double —
 * Fintech-Präzision). {@code quantity} ist {@link BigDecimal}, um Bruchstücke
 * (Sparpläne, fraktionale Anteile) zu erlauben. Invarianten werden im Konstruktor erzwungen.
 *
 * @param isin       Wertpapier-Kennnummer (MVP: ISIN), nicht leer
 * @param quantity   Stückzahl, > 0
 * @param buyInPrice initialer Kaufkurs pro Stück in EUR, >= 0
 */
public record Position(String isin, BigDecimal quantity, BigDecimal buyInPrice) {

    public Position {
        if (isin == null || isin.isBlank()) {
            throw new IllegalArgumentException("isin darf nicht leer sein");
        }
        if (quantity == null || quantity.signum() <= 0) {
            throw new IllegalArgumentException("quantity muss > 0 sein");
        }
        if (buyInPrice == null || buyInPrice.signum() < 0) {
            throw new IllegalArgumentException("buyInPrice muss >= 0 sein");
        }
    }

    /** Historischer Kaufwert dieser Position: quantity * buyInPrice. */
    public BigDecimal historicalCost() {
        return quantity.multiply(buyInPrice);
    }
}
