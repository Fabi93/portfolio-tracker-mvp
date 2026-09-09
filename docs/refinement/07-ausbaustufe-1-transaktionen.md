# Ticket: Ausbaustufe 1 — Transaktionsbasierte Erfassung (PO)

> Ergebnis von `product-owner` / Skill `define-tickets`. Abgeleitet aus PRD §3, Ausbaustufe 1.

## Intent & Wert
Anleger bauen Positionen über Sparpläne (viele kleine Käufe) auf oder verkaufen Teile. Statt
starrer Positionen verarbeitet das System eine Liste von **Transaktionen** (`BUY`/`SELL` mit
Datum, Menge, Kurs) und leitet daraus **dynamisch** den aktuellen **Bestand** und den
**durchschnittlichen Einstiegskurs** je ISIN ab.

## Akzeptanzkriterien (AC — Vertrag)
- [ ] **AC-AB1.1** Eine `BUY`-Transaktion (ISIN, Datum, Menge > 0, Kurs ≥ 0) wird erfasst.
- [ ] **AC-AB1.2** Eine `SELL`-Transaktion wird erfasst, sofern die Menge den aktuellen Bestand der ISIN **nicht überschreitet**; andernfalls Ablehnung (Fehler), keine Erfassung.
- [ ] **AC-AB1.3** Aktueller Bestand je ISIN = Σ(BUY-Mengen) − Σ(SELL-Mengen); nur Bestände **> 0** werden ausgewiesen.
- [ ] **AC-AB1.4** Ø-Einstiegskurs je ISIN = **kostengewichteter Durchschnitt** der Käufe. Ein Verkauf reduziert die Menge zum aktuellen Ø-Kurs; der **Ø-Einstieg bleibt unverändert** (kein FIFO im MVP).
- [ ] **AC-AB1.5** Transaktionen werden **chronologisch** (nach Datum) verarbeitet.
- [ ] **AC-AB1.6** Ungültige Eingaben (fehlender Typ/ISIN/Datum, Menge ≤ 0, negativer Kurs) → Validierungsfehler (400), keine Erfassung.
- [ ] **AC-AB1.7** Beträge in EUR, `BigDecimal`; Ø-Einstieg auf 2 Nachkommastellen (HALF_EVEN).

## Subtasks
- **Happy Path:** BUY erhöht Bestand + Kostenbasis; SELL reduziert Bestand (Ø unverändert); Bestandsliste abrufbar.
- **Technische Komponenten:** Domäne `Transaction`/`TransactionType`/`Holding`/`HoldingsCalculator`; `TransactionService` (in-memory); REST `POST /api/portfolio/transactions`, `GET /api/portfolio/holdings`; Fehler-Mapping (Übermenge-Verkauf → 400).
- **Test-Komponenten:** Unit (Calculator) + API (Endpunkte).
- *(Edge/Error von QA ergänzt.)*

## Offene Entscheidungen (an Mensch)
- **Verhältnis zu US1–US3:** MVP hält die positionsbasierten Endpunkte (US1–US3) **unverändert** und ergänzt Transaktionen/Holdings **additiv** (eigener Bereich). Alternative wäre, Wert/Performance künftig aus Holdings zu speisen — bewusst *nicht* in diesem Ticket.

## Ehrliche Einschätzung
- **Ø-Einstieg-Methode** (gewichteter Durchschnitt vs. FIFO) ist eine echte fachliche Wahl; MVP = gewichteter Durchschnitt, im AC festgeschrieben. Bei realer Steuer-/Reporting-Anforderung neu bewerten.
- Chronologie bei **gleichem Datum**: stabile Reihenfolge = Eingabereihenfolge (keine Uhrzeit im MVP).
