# Refinement · Acceptance-Test-Specs (QA)

> Ergebnis von `qa-tester` / Skill `define-acceptance-tests`. Given/When/Then aus den AC. Diese Specs sind im Delivery-Schritt bereits als **ausführbare JUnit-Tests** hinterlegt (`backend/.../PortfolioServiceAcceptanceTest.java`), aktuell `@Disabled` = Test-First-Vertrag.

## US1 — Position hinzufügen

| # | Given | When | Then |
|---|---|---|---|
| T1.1 | leeres Portfolio | gültige Position (ISIN, qty=10, price=90) hinzufügen | Position ist Teil des Portfolios; historischer Kaufwert = 900 |
| T1.2 (error) | – | ISIN leer/blank | Ablehnung, keine Speicherung |
| T1.3 (error) | – | qty = 0 (oder negativ) | Ablehnung |
| T1.4 (error) | – | price < 0 | Ablehnung |
| T1.5 (edge) | – | qty = 0.5 (fraktional) | akzeptiert |

## US2 — Portfoliowert

| # | Given | When | Then |
|---|---|---|---|
| T2.1 (edge) | leeres Portfolio | Gesamtwert abfragen | `0` EUR |
| T2.2 | AAA: qty=10 @Kurs 100; BBB: qty=4 @Kurs 50 | Gesamtwert abfragen | `1200.00` EUR |
| T2.3 | eine Position | Kurs ändert sich in der Quelle | Gesamtwert spiegelt neuen Kurs |
| T2.4 (edge) | qty fraktional (0.5 @ Kurs 100) | Gesamtwert | `50.00` EUR, Rundung HALF_EVEN |

## US3 — Gewinn/Verlust

| # | Given | When | Then |
|---|---|---|---|
| T3.1 | AAA qty=10 buy=80, Kurs=100 | G/V abfragen | `+200.00` EUR |
| T3.2 (loss) | BBB qty=4 buy=60, Kurs=50 | G/V abfragen | `-40.00` EUR |
| T3.3 (edge) | Kurs = Kaufkurs | G/V abfragen | `0.00` EUR |
| T3.4 (edge) | leeres Portfolio | G/V abfragen | `0.00` EUR |

## Nicht-funktionale / NFR-Testziele
- **Präzision:** keine Fließkomma-Artefakte — Beträge exakt (`BigDecimal`, `isEqualByComparingTo`).
- **Determinismus:** Kursquelle im Test über Stub/feste Werte, keine Zufalls-/Zeitabhängigkeit.
- **Performance (Richtwert MVP):** Wert-/Performance-Berechnung für 1.000 Positionen < 50 ms (in-memory).

## Härtung der AC (Bounce an PO)
- „Gleiche ISIN mehrfach" (US1) und „unbekannte ISIN ohne Kurs" (US2) waren im PRD unspezifiziert → als offene Punkte an den PO zurückgespielt (siehe `01-tickets.md`).
