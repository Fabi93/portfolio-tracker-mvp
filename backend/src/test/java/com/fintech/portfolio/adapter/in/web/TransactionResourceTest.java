package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.application.TransactionService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Ausbaustufe 1 — Acceptance-Tests (API): Transaktionen & abgeleiteter Bestand, isoliert. */
@QuarkusTest
class TransactionResourceTest {

    private static final String CT = "application/json";

    @Inject
    TransactionService transactionService;

    @BeforeEach
    void reset() {
        transactionService.clear();
    }

    private static String tx(String type, String isin, String date, String qty, String price) {
        return "{\"type\":\"" + type + "\",\"isin\":\"" + isin + "\",\"date\":\"" + date
                + "\",\"quantity\":" + qty + ",\"price\":" + price + "}";
    }

    @Test
    void leererBestand_wennKeineTransaktionen() {
        given().when().get("/api/portfolio/holdings")
                .then().statusCode(200).body("size()", equalTo(0));
    }

    @Test
    void zweiKaeufe_ergebenKostengewichtetenDurchschnitt() {
        given().contentType(CT).body(tx("BUY", "US0378331005", "2026-01-10", "8", "180.00"))
                .when().post("/api/portfolio/transactions").then().statusCode(201);
        given().contentType(CT).body(tx("BUY", "US0378331005", "2026-02-10", "2", "230.00"))
                .when().post("/api/portfolio/transactions").then().statusCode(201);

        var json = given().when().get("/api/portfolio/holdings")
                .then().statusCode(200).body("size()", equalTo(1))
                .body("[0].isin", equalTo("US0378331005"))
                .extract().jsonPath();
        // (8*180 + 2*230) / 10 = 190.00
        assertEquals(10.0, json.getDouble("[0].quantity"), 0.0001);
        assertEquals(190.00, json.getDouble("[0].averageBuyIn"), 0.005);
    }

    @Test
    void verkaufUeberBestand_liefert400() {
        given().contentType(CT).body(tx("BUY", "OVERSELL_TEST", "2026-01-01", "1", "10.00"))
                .when().post("/api/portfolio/transactions").then().statusCode(201);
        given().contentType(CT).body(tx("SELL", "OVERSELL_TEST", "2026-01-02", "2", "10.00"))
                .when().post("/api/portfolio/transactions").then().statusCode(400);
    }

    @Test
    void ungueltigeTransaktion_liefert400() {
        given().contentType(CT).body(tx("BUY", "IE00B4L5Y983", "2026-01-05", "0", "90.00"))
                .when().post("/api/portfolio/transactions").then().statusCode(400);
    }
}
