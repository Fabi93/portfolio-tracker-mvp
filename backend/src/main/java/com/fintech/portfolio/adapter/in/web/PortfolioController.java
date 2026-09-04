package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.application.PortfolioService;
import com.fintech.portfolio.domain.Position;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Eingangs-Adapter: REST-API für das Portfolio (Kontrakt siehe docs/refinement/backend-plan.md).
 *
 * <p>Bewusst dünn — delegiert an {@link PortfolioService}. Die eigentliche Geschäftslogik
 * (US2/US3) wird in der Delivery implementiert.
 */
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /** US1: Position hinzufügen. */
    @PostMapping("/positions")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPosition(@org.springframework.web.bind.annotation.RequestBody AddPositionRequest request) {
        portfolioService.addPosition(new Position(request.isin(), request.quantity(), request.buyInPrice()));
    }

    /** US2: aktueller Gesamtwert in EUR. */
    @GetMapping("/value")
    public MoneyResponse value() {
        return new MoneyResponse(portfolioService.totalValue(), "EUR");
    }

    /** US3: absoluter Gewinn/Verlust in EUR. */
    @GetMapping("/performance")
    public MoneyResponse performance() {
        return new MoneyResponse(portfolioService.absoluteProfitLoss(), "EUR");
    }

    public record AddPositionRequest(
            @NotBlank String isin,
            @NotNull @Positive BigDecimal quantity,
            @NotNull @PositiveOrZero BigDecimal buyInPrice) {
    }

    public record MoneyResponse(BigDecimal amount, String currency) {
    }
}
