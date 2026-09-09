package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.adapter.in.web.dto.AddPositionRequest;
import com.fintech.portfolio.adapter.in.web.dto.MoneyResponse;
import com.fintech.portfolio.adapter.in.web.dto.PositionResponse;
import com.fintech.portfolio.application.PortfolioService;
import com.fintech.portfolio.domain.Position;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Eingangs-Adapter: REST-API für das Portfolio (Kontrakt: {@code openapi.yaml}).
 * Bewusst dünn — delegiert an {@link PortfolioService}.
 */
@Path("/api/portfolio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PortfolioResource {

    private final PortfolioService portfolioService;

    public PortfolioResource(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /** US1: Position hinzufügen. Ungültige Eingaben → 400 (Bean Validation). */
    @POST
    @Path("/positions")
    public Response addPosition(@Valid AddPositionRequest request) {
        portfolioService.addPosition(new Position(request.isin(), request.quantity(), request.buyInPrice()));
        return Response.status(Response.Status.CREATED).build();
    }

    /** Positionen des Portfolios auflisten. */
    @GET
    @Path("/positions")
    public List<PositionResponse> listPositions() {
        return portfolioService.positions().stream()
                .map(PositionResponse::from)
                .toList();
    }

    /** US2: aktueller Gesamtwert des Portfolios in EUR. */
    @GET
    @Path("/value")
    public MoneyResponse value() {
        return MoneyResponse.eur(portfolioService.totalValue());
    }
}
