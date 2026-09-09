package com.fintech.portfolio.adapter.in.web;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Minimaler Lebenszeichen-Endpunkt, damit das frisch umgestellte Quarkus-Backend
 * verifizierbar startet. Die Portfolio-Endpunkte kommen mit US1–US3.
 */
@Path("/api/ping")
public class PingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }
}
