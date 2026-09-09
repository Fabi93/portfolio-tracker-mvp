package com.fintech.portfolio.application;

import com.fintech.portfolio.application.port.out.MarketPriceProvider;
import com.fintech.portfolio.domain.Position;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Zentrale Geschäftslogik des MVP.
 *
 * <p>Der Bestand wird im MVP in-memory gehalten. US1 (hinzufügen), US2 (Gesamtwert) und
 * US3 (Gewinn/Verlust) sind umgesetzt.
 */
@ApplicationScoped
public class PortfolioService {

    private static final int MONEY_SCALE = 2;

    private final MarketPriceProvider marketPriceProvider;
    private final List<Position> positions = new ArrayList<>();

    @Inject
    public PortfolioService(MarketPriceProvider marketPriceProvider) {
        this.marketPriceProvider = marketPriceProvider;
    }

    /** US1: Position hinzufügen. Invarianten werden im {@link Position}-Konstruktor erzwungen. */
    public void addPosition(Position position) {
        positions.add(position);
    }

    public List<Position> positions() {
        return List.copyOf(positions);
    }

    /**
     * US2: Aktueller Gesamtwert in EUR = Σ (quantity_i * aktueller Marktkurs_i).
     * Leeres Portfolio ⇒ 0.00. Rundung HALF_EVEN auf 2 Nachkommastellen.
     */
    public BigDecimal totalValue() {
        return positions.stream()
                .map(this::currentValueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(MONEY_SCALE, RoundingMode.HALF_EVEN);
    }

    /**
     * US3: Absoluter Gewinn/Verlust in EUR = totalValue() − historicalCost().
     * Positiv = Gewinn, negativ = Verlust. Rundung HALF_EVEN auf 2 Nachkommastellen.
     */
    public BigDecimal absoluteProfitLoss() {
        return totalValue().subtract(historicalCost()).setScale(MONEY_SCALE, RoundingMode.HALF_EVEN);
    }

    /** Gesamter historischer Kaufwert = Σ (quantity_i * buyInPrice_i). */
    public BigDecimal historicalCost() {
        return positions.stream()
                .map(Position::historicalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal currentValueOf(Position position) {
        return position.quantity().multiply(marketPriceProvider.currentPriceOf(position.isin()));
    }
}
