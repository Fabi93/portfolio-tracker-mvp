package com.fintech.portfolio.adapter.in.web.dto;

import com.fintech.portfolio.domain.Position;

import java.math.BigDecimal;

/** Response-DTO für eine Position (Liste im Portfolio). */
public record PositionResponse(String isin, BigDecimal quantity, BigDecimal buyInPrice) {

    public static PositionResponse from(Position position) {
        return new PositionResponse(position.isin(), position.quantity(), position.buyInPrice());
    }
}
