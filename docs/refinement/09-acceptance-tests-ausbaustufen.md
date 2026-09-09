# Acceptance Tests: Ausbaustufe 1 & 2 (QA)

> Ergebnis von `qa-tester` / Skill `define-acceptance-tests`. Given/When/Then aus den AC.
> Als ausführbare Tests hinterlegt (Backend: JUnit/RestAssured).

## Ausbaustufe 1 — Transaktionen → Bestand & Ø-Einstieg

### Happy Path
- **Given** keine Transaktionen · **When** BUY 10 @ 90 (ISIN X) · **Then** Bestand X = 10, Ø-Einstieg = 90.00.
- **Given** BUY 10 @ 90, dann BUY 10 @ 110 (X) · **When** Bestände · **Then** Menge 20, Ø-Einstieg = 100.00 (kostengewichtet).
- **Given** BUY 10 @ 90 (X) · **When** SELL 4 @ beliebig · **Then** Menge 6, Ø-Einstieg **unverändert** 90.00.

### Edge-Cases
- **[Edge]** Given BUY 10, SELL 10 (X) · When Bestände · Then ISIN X **nicht** mehr gelistet (Menge 0).
- **[Edge]** Given BUY 0.5 @ 100 · Then Ø-Einstieg 100.00 (fraktional).
- **[Edge]** Given zwei ISINs · Then je ISIN eigener Bestand/Ø-Einstieg.

### Error-Cases
- **[Error]** Given BUY 10 (X) · When SELL 11 · Then Ablehnung 400 (Übermenge-Verkauf), Bestand unverändert.
- **[Error]** When Transaktion mit Menge 0 / negativem Kurs / fehlendem Typ/Datum · Then 400, keine Erfassung.

## Ausbaustufe 2 — Benchmark-Vergleich

### Happy Path
- **Given** Portfolio mit Kaufwert 900 und Gesamtwert 1025 (Rendite ≈ 13.89 %), Benchmark `MSCI_WORLD` = 8.00 % · **When** Vergleich · **Then** Outperformance ≈ +5.89 Prozentpunkte.

### Edge-Cases
- **[Edge]** Given leeres Portfolio (Kaufwert 0) · When Vergleich · Then Portfolio-Rendite 0.00, Outperformance = −Benchmark%.

### Error-Cases
- **[Error]** When unbekannte Benchmark-ID · Then 400 (keine erfundene Zahl).

## Performance-/NFR-Ziele (verbindlich)
- Bestands-/Vergleichsberechnung für 1.000 Transaktionen < 50 ms (in-memory).
- Deterministisch: simulierte Kurse/Benchmark-Renditen fest; keine Zufalls-/Zeitabhängigkeit in Tests.

## Bounces an PO
- Keine — die AC sind aus dem PRD ableitbar (Ø-Einstieg-Methode & Rendite-Basis im Ticket festgeschrieben).
