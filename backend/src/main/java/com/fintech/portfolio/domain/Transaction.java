package com.fintech.portfolio.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Eine Transaktion (Ausbaustufe 1: Event-Sourcing-Light).
 *
 * @param type     BUY oder SELL
 * @param isin     Wertpapier-Kennnummer, nicht leer
 * @param date     Handelsdatum, nicht null
 * @param quantity Menge, > 0
 * @param price    Kurs pro Stück in EUR, >= 0
 */
public record Transaction(TransactionType type, String isin, LocalDate date, BigDecimal quantity, BigDecimal price) {

    public Transaction {
        if (type == null) {
            throw new IllegalArgumentException("type darf nicht null sein");
        }
        if (isin == null || isin.isBlank()) {
            throw new IllegalArgumentException("isin darf nicht leer sein");
        }
        if (date == null) {
            throw new IllegalArgumentException("date darf nicht null sein");
        }
        if (quantity == null || quantity.signum() <= 0) {
            throw new IllegalArgumentException("quantity muss > 0 sein");
        }
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("price muss >= 0 sein");
        }
    }
}
