package com.fintech.portfolio.adapter.in.web.dto;

import com.fintech.portfolio.domain.Holding;

import java.math.BigDecimal;

/** Response-DTO für einen abgeleiteten Bestand (Ausbaustufe 1). */
public record HoldingResponse(String isin, BigDecimal quantity, BigDecimal averageBuyIn) {

    public static HoldingResponse from(Holding holding) {
        return new HoldingResponse(holding.isin(), holding.quantity(), holding.averageBuyIn());
    }
}
