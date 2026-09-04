---
name: write-acceptance-tests
description: DELIVERY (Test-First) — der QA-Anteil in der Umsetzung. Aus den freigegebenen Akzeptanzkriterien (AC) eines reifen Tickets die AUSFÜHRBAREN Acceptance Tests schreiben, BEVOR implementiert wird (rot). Getrennt für Backend (ausführbare API-/Acceptance-Tests) und Frontend (Playwright E2E). Deckt Happy Path, Edge Cases, Error Cases ab UND die im Refinement ergänzten Compliance- & Security-Vorgaben. Die Tests sind der Vertrag, gegen den beide Developer implementieren.
---

# Skill: Acceptance Tests schreiben (Delivery, Test-First)

## Was das ist — und was nicht
Aus den **freigegebenen AC** eines reifen Tickets werden **ausführbare Acceptance Tests** — **vor** der Implementierung (test-first/ATDD). Sie sind zuerst **rot** und definieren „fertig". **Nicht** Unit-Tests der Developer (die schreiben BE/FE selbst), **nicht** Implementierung, **nicht** Ticket-Schnitt.
- Kernaufgabe: Der **ausführbare Vertrag**, gegen den Backend **und** Frontend parallel implementieren.

## Voraussetzung
Ein **reifes Ticket** (Refinement/DoR): freigegebene AC + Case-Subtasks (Happy/Edge/Error) + **Compliance-Findings** + **Security-Tests** aus dem Refinement.

## Prozess
1. **Vertrag laden.** AC + Compliance- + Security-Vorgaben des Tickets. Widersprüchlich/unklar → **Bounce an PO**, nicht raten.
2. **Fälle systematisch ableiten** (aus den Subtasks): **Happy Path**, **Edge Cases** (Grenzen, leer, maximal, Duplikate, Sonderzeichen), **Error Cases** (ungültig, fehlende Pflichtfelder, fehlende Rechte).
3. **Compliance & Security als Tests mitschreiben** (Pflicht): z. B. Datenminimierung (kein unnötiges Feld persistiert), Löschung/Export, Einwilligung; sowie AuthZ, Input-Validierung, keine Datenexposition, kein Secret im Response/Log. Jede Refinement-Vorgabe → mindestens ein grüner Test.
4. **Zwei Ebenen schreiben:**
   - **Backend-Acceptance** = ausführbare **API-/Acceptance-Tests** gegen die Endpunkte des Kontrakts (Given/When/Then → HTTP-Request → Status + Body + Persistenz-Effekt). Framework des Backends (z. B. Spring `@SpringBootTest` + MockMvc/WebTestClient, gern mit Testcontainers).
   - **Frontend-Acceptance** = **Playwright E2E-Tests** entlang des Nutzerflusses (kürzester Weg): Screen-Interaktion → sichtbares Ergebnis + States (empty/loading/error/success). Selektoren über Rollen/Labels (a11y-freundlich), nicht über brüchige CSS-Pfade.
5. **Gegen den Kontrakt verankern.** Endpunkte/Schemas aus `openapi.yaml` referenzieren; wo der Kontrakt noch fehlt, die erwartete Signatur als Annahme markieren (→ BE/FE bestätigen beim Kontrakt-Erstellen).
6. **Rot verifizieren.** Tests müssen ohne Implementierung fehlschlagen (sonst testen sie nichts). Jeder Test benennt **welche AC / welchen Case / welche Compliance-/Security-Vorgabe** er abdeckt.
7. **Messbare NFR-/Performance-Ziele** je Ebene vorgeben (z. B. Antwortzeit-Budget, Renderzeit große Liste) — als prüfbare Schwelle, nicht „schnell".

## Anti-Patterns
- **Fachliche Absicht erfinden** statt beim PO klären.
- **Tests nach der Implementierung** schreiben (kein Test-First) — oder Tests, die sofort grün sind.
- **Compliance/Security vergessen** — nur den Happy Path testen.
- **Nur eine Ebene** (nur Backend oder nur Frontend) statt beider.
- **Brüchige Playwright-Selektoren** (Pixel/tiefe CSS) statt Rollen/Labels.
- **Unklare Zuordnung** — Test ohne Verweis auf AC/Case.

## Output-Format
```
# Acceptance Tests: [Ticket]
## Abdeckungs-Matrix
| AC / Case / Vorgabe | Ebene (BE/FE) | Testname | Erwartung |
|---|---|---|---|
## Backend-Acceptance (ausführbar, API)
- Test … (Given/When/Then → Status/Body/Persistenz) · deckt AC/Case … · [Compliance/Security: …]
## Frontend-Acceptance (Playwright E2E)
- Test … (Flow-Schritte → sichtbares Ergebnis + State) · deckt AC/Case … · Selektor via Rolle/Label
## Compliance- & Security-Abdeckung
- Vorgabe … → Test …
## NFR / Performance-Ziele (messbar)
- BE: … · FE: …
## Rot-Nachweis
- alle Tests schlagen ohne Implementierung fehl: ✅
## Bounces an PO
- …
```

## Quellen
ATDD/Test-First (Acceptance-Test-Driven Development); Given/When/Then (Gherkin); F.I.R.S.T.; Playwright (rollenbasierte Selektoren, Web-First-Assertions).
