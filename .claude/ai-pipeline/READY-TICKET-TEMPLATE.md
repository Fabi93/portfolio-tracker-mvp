# Reifes-Ticket-Template & DoR

_Das Output-Artefakt der Refinement-Stufe. Ein Ticket ist REIF, wenn die DoR-Checkliste erfüllt ist._

## Template

```
# Ticket: [Prozess-Schritt]   (Parent)

## Intent & Wert
- Warum / welcher Nutzen (aus Roadmap/Strategie)?

## Akzeptanzkriterien (AC — Vertrag für QA & Backend)
- [ ] …

## Acceptance Tests (QA)
- Happy: Given/When/Then …

## Subtasks
- Happy Path
- Edge-Cases:   [Edge] …        (von QA)
- Error-Cases:  [Error] …       (von QA)
- Backend-Tasks: …              (BE)
- Frontend-Tasks / UX: …        (FE, kürzeste Wege)
- Test-Komponenten: …

## Tech-Approach (Optionen von BE/FE → Entscheidung am Design-Gate durch Mensch)
- Option A … / Option B … → gewählt: …

## NFR
- Performance-Ziele (von QA vorgegeben): …
- Security-Tests (von Security-Agent): …
- Compliance/DSGVO (von Compliance-Agent): …

## DoD-Referenz
- .claude/ai-pipeline/DEFINITION_OF_DONE.md

## Größe / Notizen
- …
```

## Definition of Ready (DoR) — Gate „REIF"
Ein Ticket ist reif, wenn:
1. **Intent & Wert** klar, aus Roadmap/Strategie ableitbar.
2. **AC** testbar & eindeutig (keine offenen Bounces).
3. **≥ 1 Acceptance Test** (Happy) formuliert; Edge/Error-Cases als Subtasks identifiziert.
4. **BE- & FE-Tasks** grob geschnitten; **Größe** abgeschätzt.
5. **Tech-Approach** entschieden (Design-Gate durch Mensch).
6. **NFR gesetzt:** Perf-Ziele (QA), Security-Tests (Security), Compliance/DSGVO (Compliance) — keine blockierenden Findings.
7. **Keine offene Eskalation** an den Menschen.
8. **Split-Regel** eingehalten (kein fachliches UND/ODER im einen Ticket).

_DoR = Eintritts-Gate zur Implementierung. DoD (Delivery-„fertig", inkl. Scans) → separate Datei._
