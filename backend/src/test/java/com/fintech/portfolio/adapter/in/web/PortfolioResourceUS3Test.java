package com.fintech.portfolio.adapter.in.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/** US3 — Acceptance-Test (API): Performance-Endpunkt liefert EUR-Betrag. */
@QuarkusTest
class PortfolioResourceUS3Test {

    @Test
    void performance_liefert200MitEur() {
        given().when().get("/api/portfolio/performance")
                .then().statusCode(200)
                .body("currency", equalTo("EUR"))
                .body("amount", notNullValue());
    }
}
