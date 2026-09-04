package com.fintech.portfolio.application;

import com.fintech.portfolio.application.port.out.MarketPriceProvider;
import com.fintech.portfolio.domain.Position;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Zentrale Geschäftslogik des MVP (US1–US3).
 *
 * <p>Der Bestand wird im MVP in-memory gehalten (bewusste Vereinfachung; Persistenz-Optionen
 * siehe {@code docs/refinement/backend-plan.md}, Entscheidung am Design-Gate).
 *
 * <p><b>Delivery-Hinweis (Test-First):</b> Die Berechnungen in {@link #totalValue()} und
 * {@link #absoluteProfitLoss()} sind bewusst als TODO offen und werden im Workshop gegen die
 * (aktuell {@code @Disabled}) Acceptance-Tests implementiert. {@link #addPosition(Position)}
 * und {@link #historicalCost()} sind bereits umgesetzt.
 */
@Service
public class PortfolioService {

    private final MarketPriceProvider marketPriceProvider;
    private final List<Position> positions = new ArrayList<>();

    public PortfolioService(MarketPriceProvider marketPriceProvider) {
        this.marketPriceProvider = marketPriceProvider;
    }

    /** US1: Position hinzufügen. Validierung erfolgt im {@link Position}-Konstruktor. */
    public void addPosition(Position position) {
        positions.add(position);
    }

    public List<Position> positions() {
        return List.copyOf(positions);
    }

    /**
     * US2: Aktueller Gesamtwert des Portfolios in EUR
     * = Σ (quantity_i * aktueller Marktkurs_i).
     */
    public BigDecimal totalValue() {
        // TODO (Delivery/Workshop): über positions summieren,
        //   je Position quantity * marketPriceProvider.currentPriceOf(isin).
        //   Leeres Portfolio => 0. Rundung/Skalierung gemäß AC.
        throw new UnsupportedOperationException("US2: totalValue() wird in der Delivery implementiert");
    }

    /**
     * US3: Absoluter Gewinn/Verlust in EUR
     * = totalValue() - historicalCost().
     */
    public BigDecimal absoluteProfitLoss() {
        // TODO (Delivery/Workshop): totalValue() - historicalCost().
        throw new UnsupportedOperationException("US3: absoluteProfitLoss() wird in der Delivery implementiert");
    }

    /** Gesamter historischer Kaufwert = Σ (quantity_i * buyInPrice_i). */
    public BigDecimal historicalCost() {
        return positions.stream()
                .map(Position::historicalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
