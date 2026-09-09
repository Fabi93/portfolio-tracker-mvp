package com.fintech.portfolio.adapter.in.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/** Ausbaustufe 2 — Acceptance-Tests (API) für den Benchmark-Vergleich. */
@QuarkusTest
class BenchmarkResourceTest {

    @Test
    void bekannteBenchmark_liefert200MitWerten() {
        given().queryParam("benchmarkId", "MSCI_WORLD")
                .when().get("/api/portfolio/benchmark")
                .then().statusCode(200)
                .body("benchmarkId", equalTo("MSCI_WORLD"))
                .body("portfolioReturnPct", notNullValue())
                .body("benchmarkReturnPct", notNullValue())
                .body("outperformancePct", notNullValue());
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
