package com.fintech.portfolio.application;

import com.fintech.portfolio.domain.Position;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Zentrale Geschäftslogik des MVP.
 *
 * <p>Der Bestand wird im MVP in-memory gehalten (bewusste Vereinfachung). US1 (Position
 * hinzufügen) ist hier umgesetzt; US2 (Gesamtwert) und US3 (Gewinn/Verlust) kommen in
 * den jeweiligen Anforderungs-Commits dazu.
 */
@ApplicationScoped
public class PortfolioService {

    private final List<Position> positions = new ArrayList<>();

    /** US1: Position hinzufügen. Invarianten werden im {@link Position}-Konstruktor erzwungen. */
    public void addPosition(Position position) {
        positions.add(position);
    }

    public List<Position> positions() {
        return List.copyOf(positions);
    }

    /** Gesamter historischer Kaufwert = Σ (quantity_i * buyInPrice_i). */
    public BigDecimal historicalCost() {
        return positions.stream()
                .map(Position::historicalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
