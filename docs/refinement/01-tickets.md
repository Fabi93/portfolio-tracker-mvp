# Refinement · Tickets & Akzeptanzkriterien (PO)

> Ergebnis von `product-owner` / Skill `define-tickets`. Ein Ticket = ein Prozess-Schritt mit **testbaren** Akzeptanzkriterien (AC). Die AC sind der Vertrag für QA und die Developer.

## Gemeinsame fachliche Festlegungen (MVP)

- **Währung:** alle Beträge in **EUR**. Keine Fremdwährung im MVP.
- **Geld & Mengen:** `BigDecimal`, niemals `double`. Beträge auf 2 Nachkommastellen, Rundung **HALF_EVEN** (kaufmännisch/bankers'). Stückzahl darf fraktional sein (Sparpläne).
- **Wertpapier-Kennnummer:** im MVP als **ISIN** interpretiert (12-stellig alphanumerisch). WKN als Alternative ist Future Scope.
- **Kurse:** **simuliert** über eine austauschbare Kursquelle (Port/Adapter), deterministisch für Tests.
- **Kein Login im MVP:** Es gibt zunächst *ein* implizites Portfolio (in-memory). Multi-User/Auth ist Future Scope — hat aber Compliance-/Security-Implikationen (siehe `05-compliance-security.md`).

---

## US1 — Position hinzufügen

**Als** Anleger **möchte ich** eine Position (ISIN, Stückzahl, initialer Kaufkurs) hinzufügen, **um** meinen Bestand zu erfassen.

### Akzeptanzkriterien
- **AC1.1** Gegeben gültige Eingaben (ISIN nicht leer, Stückzahl > 0, Kaufkurs ≥ 0), wenn die Position hinzugefügt wird, dann ist sie Teil des Portfolios und fließt in Wert-/Performance-Berechnung ein.
- **AC1.2** Eine leere/fehlende ISIN wird abgelehnt (Validierungsfehler, keine Speicherung).
- **AC1.3** Eine Stückzahl ≤ 0 wird abgelehnt.
- **AC1.4** Ein negativer Kaufkurs wird abgelehnt. (Kurs = 0 ist erlaubt.)
- **AC1.5** Fraktionale Stückzahlen (z. B. `0.5`) sind erlaubt.

### Subtasks
- [x] Domänen-Wertobjekt `Position` mit Validierung *(bereits im Seed vorhanden)*
- [ ] Eingabe-Validierung im Web-Adapter (Bean Validation) + einheitliches Fehlerformat
- [ ] Acceptance-Tests grün
- [ ] (FE) Formular „Position hinzufügen" mit Inline-Validierung

### Offene Punkte (→ Human-Gate / PO)
- **Gleiche ISIN mehrfach hinzufügen?** MVP-Entscheidung: **als separate Positionen** behandeln (Zusammenführen = Ausbaustufe 1, transaktionsbasiert). *Bestätigen im Workshop.*

---

## US2 — Portfoliowert berechnen

**Als** Anleger **möchte ich** den aktuellen Gesamtwert (EUR) sehen, **berechnet als** Σ (Stückzahl × aktueller Marktkurs).

### Akzeptanzkriterien
- **AC2.1** Leeres Portfolio ⇒ Gesamtwert `0.00 EUR`.
- **AC2.2** Bei ≥ 1 Position ist der Gesamtwert die Summe aus `Stückzahl × aktueller Marktkurs` je Position.
- **AC2.3** Das Ergebnis ist auf 2 Nachkommastellen gerundet (HALF_EVEN), Währung `EUR`.
- **AC2.4** Kurse stammen aus der (simulierten) Kursquelle; die Berechnung ist von der konkreten Quelle entkoppelt.

### Subtasks
- [ ] `PortfolioService.totalValue()` implementieren *(TODO im Seed)*
- [ ] Kurs-Port/Adapter anbinden *(Simulations-Adapter im Seed vorhanden)*
- [ ] Acceptance-Tests grün (inkl. leeres Portfolio)

### Offene Punkte
- **Unbekannte ISIN / kein Kurs verfügbar:** MVP-Simulation liefert immer einen Kurs. Für eine echte Quelle: Fehler vs. Position mit Wert 0 ausweisen? *Diskussion Systemdesign.*

---

## US3 — Gewinn/Verlust (Performance) ausweisen

**Als** Anleger **möchte ich** den absoluten G/V (EUR) sehen: `Aktueller Gesamtwert − Gesamter historischer Kaufwert`.

### Akzeptanzkriterien
- **AC3.1** `historischer Kaufwert = Σ (Stückzahl × Kaufkurs)`.
- **AC3.2** `G/V = totalValue() − historicalCost()`.
- **AC3.3** Gewinn wird positiv, Verlust negativ ausgewiesen; Ergebnis 2 Nachkommastellen, `EUR`.
- **AC3.4** Leeres Portfolio ⇒ G/V `0.00 EUR`.

### Subtasks
- [ ] `PortfolioService.absoluteProfitLoss()` implementieren *(TODO im Seed)*
- [ ] Acceptance-Tests grün (Gewinn, Verlust, Null)
- [ ] (FE) G/V mit Vorzeichen/Farbe darstellen

### Offene Punkte
- **Prozentuale Rendite** ist bewusst *nicht* Teil des MVP (kommt mit Ausbaustufe 2 Benchmark). Nur absoluter G/V.
