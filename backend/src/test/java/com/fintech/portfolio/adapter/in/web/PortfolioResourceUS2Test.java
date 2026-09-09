package com.fintech.portfolio.adapter.in.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/** US2 — Acceptance-Test (API): Gesamtwert-Endpunkt liefert EUR-Betrag. */
@QuarkusTest
class PortfolioResourceUS2Test {

    @Test
    void gesamtwert_liefert200MitEur() {
        given().when().get("/api/portfolio/value")
                .then().statusCode(200)
                .body("currency", equalTo("EUR"))
                .body("amount", notNullValue());
    }
}
