# AI-Pipeline — Delivery-Stufe (Design)

_Teil der Pipeline → siehe [`agentic-setup.md`](./agentic-setup.md), [`REFINEMENT.md`](./REFINEMENT.md), [`DEFINITION_OF_DONE.md`](./DEFINITION_OF_DONE.md)._

Die Stufe, wo aus **reifen Tickets** (DoR) **fertiger, getesteter, gescannter Code** wird — mündend in einen **echten GitHub-MR**.

- **Input:** ein reifes Ticket aus dem Refinement (freigegebene AC · Case-Subtasks · Compliance-Findings · Security-Tests).
- **Output:** ein **fertiger echter GitHub-Pull-Request** — Code + Tests grün + Scans, **alle Ergebnisse & offene Punkte in der PR-Description**.
- **Austritts-Gate:** **DoD** — und zwar am **fertigen MR** (der Mensch reviewt/merged).

## Prinzip (Entscheidung des Nutzers)
**Kontrolle, nicht Automatisierung — aber wenige Zwischen-Gates.** Anders als im Refinement (mehrere Human-Gates) verschiebt Delivery die Kontrolle **ans Ende**: der Mensch stoppt nicht an vier Zwischenschritten, sondern **reviewt den fertigen MR**, in dem alles steht. Zwischenstopp nur bei echter fachlicher Frage (→ PO) oder fehlender Reife (→ Refinement).

## Gleiche Agents wie im Refinement — neue Skills
| Agent | Rolle in Delivery | Skill |
|---|---|---|
| **Delivery-Orchestrator** (Haupt-Agent) | routet · hält Test-First & 2-Iter-Regel · assembliert den PR | `delivery-orchestrator` (neu) |
| **QA / Tester** | schreibt **vorab** ausführbare Acceptance Tests (BE=API, FE=Playwright), rot | `write-acceptance-tests` |
| **Backend-Dev** | Kontrakt + Implementierung + Unit/Integration (Testcontainers)/API; Clean Code; Diff-Review | `implement-backend` |
| **Frontend-Dev** | Kontrakt + Implementierung + Unit/Integration; Playwright grün; UX/a11y; Clean Code; Diff-Review | `implement-frontend` |
| **Security** | führt die **Scans real aus** (Sonar/Dependency/Image/npm audit), 2 Iterationen | `run-scans` |
| **Compliance** | Vorgaben aus dem Refinement sind als Acceptance-Tests abgedeckt (Prüfbezug) | (aus Refinement) |

## Zwei harte Regeln (nicht verhandelbar)
1. **Test-First bleibt Test-First.** QA schreibt die Acceptance Tests **vor** der Implementierung; sie sind zuerst **rot**. Code wird an die Tests angepasst — **nie umgekehrt**.
2. **Genau 2 Review-Iterationen.** Nach zweimal Scannen/Reviewen ist Schluss; was offen bleibt, wird **transparent in die MR-Description** geschrieben (Schweregrad + Grund/Plan), nicht endlos poliert.

## Der API-Kontrakt entkoppelt (ermöglicht Parallelität)
**`openapi.yaml`** ist der **gemeinsame, bindende Vertrag** zwischen Backend und Frontend. BE und FE erstellen ihn **zusammen**, danach implementieren beide **parallel** dagegen. So brauchen sie einander nicht abzuwarten; der Kontrakt + die Acceptance-Tests sind die Synchronisationspunkte.

## Protokoll / Fluss
```
Reifes Ticket (DoR)
   │   ┌──────────── DELIVERY-ORCHESTRATOR ────────────┐
   ▼   │ routet · Test-First · 2-Iter · assembliert MR   │
 [QA]  Acceptance Tests VORAB (test-first, ROT)
   │     • Backend  = ausführbare API-/Acceptance-Tests
   │     • Frontend = Playwright E2E
   │     • + Compliance- & Security-Vorgaben abgedeckt
   ▼
 [BE+FE] gemeinsam  openapi.yaml  (bindender Kontrakt)
   │
   ├──────────────┬──────────────  ← PARALLEL
 [Backend-Dev]  [Frontend-Dev]
  gg. Kontrakt    gg. Kontrakt
  + Acceptance    + Playwright
  + Unit          + Unit/Komponente
  + Integration   + Integration
   (Testcont.)    UX/States/a11y
  Clean Code      Clean Code
   └──────┬───────┘
          ▼  alle Tests GRÜN
 [Review — GENAU 2 Iterationen]
   • Security: Sonar-Container · Dependency · Image · npm audit  (echte Werte)
   • BE/FE: Clean-Code-Review auf dem jeweiligen Diff
   • Iter 1: kritische Findings fixen → Iter 2: erneut → STOPP
          ▼
 [Orchestrator] Branch → Commit → Push → `gh pr create`
   • PR-Description: Acceptance-Ergebnisse · Test-Suite · Scan-Werte
     · Clean-Code-Review · noch OFFEN · DoD-Abgleich
          ▼
 ★ HUMAN-GATE: der Mensch reviewt/merged den fertigen MR (kein Auto-Merge)
```

## DoD-Bezug (Austritts-Gate)
Delivery erfüllt die [`DEFINITION_OF_DONE.md`](./DEFINITION_OF_DONE.md): ≥1 Acceptance Test · Scope-Treue · kein Slop · Clean Code/Architektur · Security-Tests grün · Performance-Ziele · **Delivery-Scans dokumentiert** (SonarQube/Dependency/Container/npm audit). Der Nachweis steht **im MR**, nicht in einer Behauptung — „demonstrated, not claimed".

## Leitplanken
- **Fachliche Frage → PO.** **Sicherheit/Compliance unklar → eskalieren.** Nicht raten.
- **Echte Werte:** Scan-Zahlen sind gemessen; nicht lauffähig → „nicht ausgeführt: Grund".
- **Erlaubte Aktionen:** Branch/Commit/Push/PR (Nutzer wünscht echten MR). **Kein Merge** ohne Menschen. **Keine Secrets** in Commits/PR-Text.
