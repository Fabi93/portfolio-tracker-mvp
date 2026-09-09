package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.adapter.in.web.dto.AddTransactionRequest;
import com.fintech.portfolio.adapter.in.web.dto.HoldingResponse;
import com.fintech.portfolio.application.TransactionService;
import com.fintech.portfolio.domain.Transaction;
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
 * Eingangs-Adapter für Ausbaustufe 1: Transaktionen erfassen und abgeleitete Bestände abrufen.
 */
@Path("/api/portfolio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /** Transaktion (BUY/SELL) erfassen. Übermenge-Verkauf oder ungültige Eingaben → 400. */
    @POST
    @Path("/transactions")
    public Response addTransaction(@Valid AddTransactionRequest request) {
        transactionService.add(new Transaction(
                request.type(), request.isin(), request.date(), request.quantity(), request.price()));
        return Response.status(Response.Status.CREATED).build();
    }

    /** Aus den Transaktionen abgeleitete Bestände (Menge + Ø-Einstiegskurs) je ISIN. */
    @GET
    @Path("/holdings")
    public List<HoldingResponse> holdings() {
        return transactionService.holdings().stream()
                .map(HoldingResponse::from)
                .toList();
    }
}
