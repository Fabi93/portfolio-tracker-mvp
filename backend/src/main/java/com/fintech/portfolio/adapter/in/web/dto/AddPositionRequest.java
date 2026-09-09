package com.fintech.portfolio.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/** Request-DTO für US1 (Position hinzufügen). Serverseitige Validierung → 400 bei Verstoß. */
public record AddPositionRequest(
        @NotBlank String isin,
        @NotNull @Positive BigDecimal quantity,
        @NotNull @PositiveOrZero BigDecimal buyInPrice) {
}
