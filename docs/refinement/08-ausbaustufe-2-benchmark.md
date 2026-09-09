# Ticket: Ausbaustufe 2 — Benchmark-Vergleich (PO)

> Ergebnis von `product-owner` / Skill `define-tickets`. Abgeleitet aus PRD §3, Ausbaustufe 2.

## Intent & Wert
Ein absoluter Gewinn sagt wenig ohne Referenz: 5 % sind schwach, wenn der Markt 15 % machte.
Der Nutzer gibt eine **Benchmark-ID** an; das System stellt die **prozentuale Rendite** des
Portfolios der des Benchmarks gegenüber und weist die **Out-/Underperformance** aus.

## Akzeptanzkriterien (AC — Vertrag)
- [ ] **AC-AB2.1** Der Nutzer gibt eine **Benchmark-ID** an (z. B. `MSCI_WORLD`).
- [ ] **AC-AB2.2** Portfolio-Rendite % = `(Gesamtwert − historischer Kaufwert) / historischer Kaufwert × 100` (Basis: positionsbasiertes Portfolio aus US1–US3).
- [ ] **AC-AB2.3** Benchmark-Rendite % stammt aus einer (simulierten) Quelle je Benchmark-ID.
- [ ] **AC-AB2.4** **Out-/Underperformance** (Prozentpunkte) = Portfolio-Rendite % − Benchmark-Rendite %.
- [ ] **AC-AB2.5** Leeres Portfolio bzw. historischer Kaufwert = 0 → Portfolio-Rendite `0` (kein Division-durch-0-Fehler).
- [ ] **AC-AB2.6** Unbekannte Benchmark-ID → Fehler (400), keine erfundene Zahl.
- [ ] **AC-AB2.7** Prozentwerte auf 2 Nachkommastellen (HALF_EVEN).

## Subtasks
- **Happy Path:** bekannte Benchmark-ID → Portfolio-%, Benchmark-%, Differenz.
- **Technische Komponenten:** `BenchmarkReturnProvider` (Out-Port) + simulierter Adapter (Map Benchmark-ID → %); `PerformanceService.compare(benchmarkId)`; REST `GET /api/portfolio/benchmark?benchmarkId=…`; Fehler-Mapping (unbekannte ID → 400).
- **Test-Komponenten:** Unit (Rendite-/Differenz-Rechnung, Division-durch-0) + API.
- *(Edge/Error von QA ergänzt.)*

## Offene Entscheidungen (an Mensch)
- **Rendite-Basis:** positionsbasiert (US1–US3), nicht transaktionsbasiert (AB1) — bewusst, um AB1/AB2 unabhängig zu halten.
- **Benchmark-Renditen** sind im MVP **simuliert/statisch** je ID; echte Zeitreihen/Zeiträume sind Future Scope.

## Ehrliche Einschätzung
- Ohne Zeitraum-Bezug ist der Vergleich vereinfacht (Punkt-in-Zeit-%); für echte Aussagekraft braucht es gleiche Zeiträume — hier bewusst MVP.
