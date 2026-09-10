package com.fintech.portfolio.adapter.in.web;

import com.fintech.portfolio.application.PortfolioService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * US1 — Acceptance-Tests (API) für „Position hinzufügen", isoliert je Test.
 * Happy Path (201) + Error Cases (400) gemäß AC1.1–AC1.5.
 */
@QuarkusTest
class PortfolioResourceUS1Test {

    private static final String CT = "application/json";

    @Inject
    PortfolioService portfolioService;

    @BeforeEach
    void reset() {
        portfolioService.clear();
    }

    @Test
    void gueltigePosition_liefert201() {
        given().contentType(CT)
                .body("{\"isin\":\"IE00B4L5Y983\",\"quantity\":10,\"buyInPrice\":90.00}")
                .when().post("/api/portfolio/positions")
                .then().statusCode(201);
    }

    @Test
    void fraktionaleStueckzahl_liefert201() {
        given().contentType(CT)
                .body("{\"isin\":\"IE00B4L5Y983\",\"quantity\":0.5,\"buyInPrice\":90.00}")
                .when().post("/api/portfolio/positions")
                .then().statusCode(201);
    }

    @Test
    void leereIsin_liefert400() {
        given().contentType(CT)
                .body("{\"isin\":\"\",\"quantity\":10,\"buyInPrice\":90.00}")
                .when().post("/api/portfolio/positions")
                .then().statusCode(400);
    }

    @Test
    void stueckzahlNull_liefert400() {
        given().contentType(CT)
                .body("{\"isin\":\"IE00B4L5Y983\",\"quantity\":0,\"buyInPrice\":90.00}")
                .when().post("/api/portfolio/positions")
                .then().statusCode(400);
    }

    @Test
    void negativerKaufkurs_liefert400() {
        given().contentType(CT)
                .body("{\"isin\":\"IE00B4L5Y983\",\"quantity\":10,\"buyInPrice\":-1}")
                .when().post("/api/portfolio/positions")
                .then().statusCode(400);
    }

    @Test
    void hinzugefuegtePosition_erscheintInDerListe() {
        given().contentType(CT)
                .body("{\"isin\":\"US0378331005\",\"quantity\":3,\"buyInPrice\":150.00}")
                .when().post("/api/portfolio/positions")
                .then().statusCode(201);

        given().when().get("/api/portfolio/positions")
                .then().statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].isin", equalTo("US0378331005"));
    }
}
