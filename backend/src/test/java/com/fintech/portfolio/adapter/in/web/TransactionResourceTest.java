package com.fintech.portfolio.adapter.in.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/** Ausbaustufe 1 — Acceptance-Tests (API) für Transaktionen und Bestände. */
@QuarkusTest
class TransactionResourceTest {

    private static final String CT = "application/json";

    @Test
    void buyTransaktion_liefert201() {
        given().contentType(CT)
                .body("{\"type\":\"BUY\",\"isin\":\"IE00B4L5Y983\",\"date\":\"2026-01-05\",\"quantity\":10,\"price\":90.00}")
                .when().post("/api/portfolio/transactions")
                .then().statusCode(201);
    }

    @Test
    void bestaende_liefert200() {
        given().when().get("/api/portfolio/holdings")
                .then().statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void verkaufUeberBestand_liefert400() {
        given().contentType(CT)
                .body("{\"type\":\"BUY\",\"isin\":\"OVERSELL_TEST\",\"date\":\"2026-01-01\",\"quantity\":1,\"price\":10.00}")
                .when().post("/api/portfolio/transactions")
                .then().statusCode(201);

        given().contentType(CT)
                .body("{\"type\":\"SELL\",\"isin\":\"OVERSELL_TEST\",\"date\":\"2026-01-02\",\"quantity\":2,\"price\":10.00}")
                .when().post("/api/portfolio/transactions")
                .then().statusCode(400);
    }

    @Test
    void ungueltigeTransaktion_liefert400() {
        given().contentType(CT)
                .body("{\"type\":\"BUY\",\"isin\":\"IE00B4L5Y983\",\"date\":\"2026-01-05\",\"quantity\":0,\"price\":90.00}")
                .when().post("/api/portfolio/transactions")
                .then().statusCode(400);
    }
}
