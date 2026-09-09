package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.adapter.in.web.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Bildet fachliche Regelverstöße ({@link IllegalArgumentException} aus der Domäne, z. B.
 * Übermenge-Verkauf) auf HTTP 400 mit einheitlichem Fehlerkörper ab.
 */
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage()))
                .build();
    }
}
