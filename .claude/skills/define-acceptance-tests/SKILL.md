---
name: define-acceptance-tests
description: Aus Akzeptanzkriterien (AC) ausführbare ACCEPTANCE TESTS ableiten (Given/When/Then), Edge-/Error-Fälle systematisch aufdecken und als Case-Subtasks erzeugen, die Testbarkeit der AC härten (Bounce an PO bei Mehrdeutigkeit) und messbare Performance-/NFR-Testziele vorgeben. Der QA-Anteil im Refinement. Erfindet keine fachliche Absicht — eskaliert. Human-Gate.
---

# Skill: Acceptance Tests & Case-Aufdeckung

## Was das ist — und was nicht
Aus den **AC** (dem Vertrag) werden **ausführbare Acceptance Tests** und die **Edge-/Error-Case-Subtasks**. QA ist die adversariale Instanz gegen Slop — der Test *ist* der Richter, nicht das Bauchgefühl.
- **Keine** Implementierung, **kein** Ticket-Schnitt (PO), **keine** Architektur.

## Voraussetzung
Ein Ticket mit **freigegebenen AC** (Skill `define-tickets`).

## Prozess

### 1 — Happy-Path-Test (mind. 1)
Pro AC mindestens **ein** Acceptance Test in **Given / When / Then**. Konkret, mit realen Werten.

### 2 — Edge-/Error-Fälle systematisch aufdecken
Nicht raten — **checklistengetrieben**: Grenzwerte (0, 1, max), leere/fehlende Eingaben, ungültige/mehrdeutige Eingaben, Duplikate, Reihenfolge/Nebenläufigkeit, Rechte/Rollen, Fehler-/Ausnahmepfade, Idempotenz.
Jeder **reale** Fund → ein **Case-Subtask** (Edge oder Error) am Ticket. Keine spekulativen Fälle.

### 3 — Testbarkeits-Härtetest (Bounce)
Ist eine AC **mehrdeutig oder nicht testbar** → **zurück an PO** (Bounce), nicht selbst interpretieren.

### 4 — Performance-/NFR-Testziele vorgeben
QA gibt **messbare** Ziele vor (z. B. „Liste mit N Einträgen < X ms"), die BE + FE einhalten müssen. Echtzeit-nah, kurze Ladezeiten auch bei großen Datenmengen.

### 5 — Human-Gate
Acceptance Tests + Case-Subtasks + Bounces + Perf-Ziele vorlegen; ehrliche Notiz, wo die AC die Absicht noch **nicht** einfängt.

## Anti-Patterns
- **Nur Happy Path** — Edge/Error verschluckt (der klassische KI-Slop).
- **Vage Tests** ohne konkrete Werte.
- **AC selbst umgedeutet** statt Bounce.
- **Perf-Ziele unmessbar** („soll schnell sein").

## Output-Format
```
# Acceptance Tests: [Ticket]
## Happy Path
- Given … / When … / Then …
## Edge-Cases (→ Subtasks)
- [Edge] Given … / When … / Then …
## Error-Cases (→ Subtasks)
- [Error] Given … / When … / Then …
## Performance-/NFR-Ziele (verbindlich für BE+FE)
- …
## Bounces an PO (AC untestbar/mehrdeutig)
- …
```

## Quellen
Gherkin / Given-When-Then (ATDD/BDD); Boundary-Value- & Equivalence-Partition-Testing; NFR-/Performance-Budgets.
