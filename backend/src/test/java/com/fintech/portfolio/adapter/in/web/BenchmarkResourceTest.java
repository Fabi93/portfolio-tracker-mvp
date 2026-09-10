package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.application.PortfolioService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Ausbaustufe 2 — Acceptance-Tests (API): Benchmark-Vergleich, isoliert und mit exakten Werten. */
@QuarkusTest
class BenchmarkResourceTest {

    private static final String CT = "application/json";

    @Inject
    PortfolioService portfolioService;

    @BeforeEach
    void reset() {
        portfolioService.clear();
    }

    @Test
    void bekannteBenchmark_liefertExakteOutperformance() {
        // Wert 1025 / Kaufwert 900 -> 13.89 %; Benchmark MSCI_WORLD 8.00 % -> +5.89 pp
        given().contentType(CT)
                .body("{\"isin\":\"IE00B4L5Y983\",\"quantity\":10,\"buyInPrice\":90.00}")
                .when().post("/api/portfolio/positions").then().statusCode(201);

        var json = given().queryParam("benchmarkId", "MSCI_WORLD")
                .when().get("/api/portfolio/benchmark")
                .then().statusCode(200)
                .body("benchmarkId", equalTo("MSCI_WORLD"))
                .extract().jsonPath();

        assertEquals(13.89, json.getDouble("portfolioReturnPct"), 0.005);
        assertEquals(8.00, json.getDouble("benchmarkReturnPct"), 0.005);
        assertEquals(5.89, json.getDouble("outperformancePct"), 0.005);
    }

    @Test
    void leeresPortfolio_underperformtUmBenchmark() {
        double outperformance = given().queryParam("benchmarkId", "MSCI_WORLD")
                .when().get("/api/portfolio/benchmark")
                .then().statusCode(200)
                .extract().jsonPath().getDouble("outperformancePct");
        assertEquals(-8.00, outperformance, 0.005);
    }

    @Test
    void unbekannteBenchmark_liefert400() {
        given().queryParam("benchmarkId", "GIBT_ES_NICHT")
                .when().get("/api/portfolio/benchmark")
                .then().statusCode(400);
    }

    @Test
    void fehlendeBenchmarkId_liefert400() {
        given().when().get("/api/portfolio/benchmark")
                .then().statusCode(400);
    }
}
