package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.application.PortfolioService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** US3 — Acceptance-Tests (API): Gewinn/Verlust, isoliert und mit exakten Werten. */
@QuarkusTest
class PortfolioResourceUS3Test {

    private static final String CT = "application/json";

    @Inject
    PortfolioService portfolioService;

    @BeforeEach
    void reset() {
        portfolioService.clear();
    }

    @Test
    void leeresPortfolio_liefertNull() {
        double amount = given().when().get("/api/portfolio/performance")
                .then().statusCode(200)
                .extract().jsonPath().getDouble("amount");
        assertEquals(0.0, amount, 0.005);
    }

    @Test
    void gewinn_istWertMinusKaufwert() {
        // Wert 10*102.50 = 1025.00, Kaufwert 10*90 = 900.00 -> +125.00
        given().contentType(CT)
                .body("{\"isin\":\"IE00B4L5Y983\",\"quantity\":10,\"buyInPrice\":90.00}")
                .when().post("/api/portfolio/positions").then().statusCode(201);

        double amount = given().when().get("/api/portfolio/performance")
                .then().statusCode(200)
                .body("currency", org.hamcrest.Matchers.equalTo("EUR"))
                .extract().jsonPath().getDouble("amount");
        assertEquals(125.00, amount, 0.005);
    }
}
