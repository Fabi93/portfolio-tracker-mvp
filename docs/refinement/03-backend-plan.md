# Refinement · Backend-Plan (Backend-Developer)

> Ergebnis von `backend-developer` / Skill `define-backend-plan`. **Planung, kein Code.** Tech-Optionen werden vorgelegt — die Wahl fällt am Human-Design-Gate.

## Datenmodell

```
Position (Wertobjekt)
  isin: String            # nicht leer (MVP: ISIN)
  quantity: BigDecimal    # > 0, fraktional erlaubt
  buyInPrice: BigDecimal  # >= 0, EUR

Portfolio (Aggregat, MVP)
  positions: List<Position>
  # abgeleitet: totalValue(), historicalCost(), absoluteProfitLoss()
```

## API-Kontrakt (Skizze, wird in Delivery als `openapi.yaml` finalisiert)

| Methode | Pfad | Zweck | Antwort |
|---|---|---|---|
| `POST` | `/api/portfolio/positions` | US1: Position anlegen | `201 Created` |
| `GET` | `/api/portfolio/value` | US2: Gesamtwert | `{ amount, currency }` |
| `GET` | `/api/portfolio/performance` | US3: absoluter G/V | `{ amount, currency }` |

Fehlerfälle: `400` mit einheitlichem Fehlerobjekt bei Validierungsverstoß (US1).

## Architektur (Clean Architecture / Ports & Adapters)

```
adapter.in.web       → PortfolioController (REST)
application          → PortfolioService (Use Cases US1–US3)
application.port.out → MarketPriceProvider (Port)
domain               → Position (Wertobjekt, Invarianten)
adapter.out.price    → SimulatedMarketPriceProvider (Adapter)
```

Die Geschäftslogik kennt nur den **Port** `MarketPriceProvider` — die simulierte Quelle ist austauschbar (echte Kursquelle = Future Scope, ohne Logikänderung).

## Tech-Approach — OPTIONEN (Entscheidung am Design-Gate)

| Aspekt | Option A (empfohlen für MVP) | Option B |
|---|---|---|
| **Persistenz** | In-Memory (Liste im Service) — reicht für 30-Min-Logik & Tests | JPA + PostgreSQL (Testcontainers) — sobald Multi-User/Persistenz gebraucht wird |
| **Kursquelle** | Simulierter Adapter (deterministisch) | Externe Kurs-API hinter demselben Port |
| **Geldtyp** | `BigDecimal` + `RoundingMode.HALF_EVEN` | dediziertes `Money`-Value-Object (falls Multi-Currency) |

> Der Seed implementiert **Option A**. Umstieg auf B ist durch die Port-Struktur lokal begrenzt.

## Risiken / Unbekannte
- Rundungs-/Skalierungsregeln fachlich final bestätigen (2 Stellen, HALF_EVEN).
- Verhalten bei fehlendem Kurs (echte Quelle) — siehe offene Punkte.
- Ohne Auth kein Multi-Tenant — Sicherheits-/Compliance-Implikation (siehe `05-`).

## Größe
US1–US3 zusammen: **S** (die Logik ist klein; der Wert liegt in Präzision, Tests, sauberer Struktur).
