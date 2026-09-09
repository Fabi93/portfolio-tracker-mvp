package com.fintech.portfolio.adapter.in.web.dto;

import com.fintech.portfolio.domain.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Request-DTO für eine Transaktion (Ausbaustufe 1). */
public record AddTransactionRequest(
        @NotNull TransactionType type,
        @NotBlank String isin,
        @NotNull LocalDate date,
        @NotNull @Positive BigDecimal quantity,
        @NotNull @PositiveOrZero BigDecimal price) {
}
