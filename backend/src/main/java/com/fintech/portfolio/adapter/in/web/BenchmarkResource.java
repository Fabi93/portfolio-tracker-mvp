package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.adapter.in.web.dto.BenchmarkResponse;
import com.fintech.portfolio.application.BenchmarkService;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * Eingangs-Adapter für Ausbaustufe 2: Benchmark-Vergleich.
 * Unbekannte Benchmark-ID → 400 (via {@link IllegalArgumentExceptionMapper}).
 */
@Path("/api/portfolio")
@Produces(MediaType.APPLICATION_JSON)
public class BenchmarkResource {

    private final BenchmarkService benchmarkService;

    public BenchmarkResource(BenchmarkService benchmarkService) {
        this.benchmarkService = benchmarkService;
    }

    @GET
    @Path("/benchmark")
    public BenchmarkResponse benchmark(@QueryParam("benchmarkId") @NotBlank String benchmarkId) {
        return BenchmarkResponse.from(benchmarkService.compareWith(benchmarkId));
    }
}
