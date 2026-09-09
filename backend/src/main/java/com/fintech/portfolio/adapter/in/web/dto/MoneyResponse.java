package com.fintech.portfolio.adapter.in.web.dto;

import java.math.BigDecimal;

/** Response-DTO für einen Geldbetrag (Gesamtwert, Gewinn/Verlust). */
public record MoneyResponse(BigDecimal amount, String currency) {

    public static MoneyResponse eur(BigDecimal amount) {
        return new MoneyResponse(amount, "EUR");
    }
}
