# AI-Pipeline — Refinement-Stufe (Design)

_Überblick der Pipeline: siehe [`agentic-setup.md`](./agentic-setup.md)._

Die Stufe, wo **PO-Track auf Dev-Track** trifft: aus einem Roadmap-Item werden **reife, umsetzbare Tickets**.

- **Input:** ein Roadmap-„Now"-Item (ein Prozess-Schritt).
- **Output:** reife Tickets — jedes winzig, verifizierbar, **DoR erfüllt**.
- **Austritts-Gate:** DoR (nicht DoD — die ist das Delivery-Gate der späteren Stufen).

## Zwei harte Regeln (nicht verhandelbar)
1. **Der Orchestrator stoppt an Human-Gates.** Zwischen Gates loopt er frei; an Gates entscheidet der Mensch.
2. **Der PO-Agent erfindet keine Produktentscheidungen.** Er beantwortet nur, was aus Vision/Strategie/AC **ableitbar** ist. Echte, noch nicht getroffene Entscheidungen → **Eskalation an den Menschen.**

## Agenten-Roster
| Agent | Rolle im Refinement |
|---|---|
| **Orchestrator** (Haupt-Agent) | routet · sammelt Artefakte · hält die Loop · setzt die Gates · fasst Offenes zusammen |
| **PO** | Roadmap-Item → Ticket-Scheiben + **AC**; beantwortet fachliche Fragen (oder eskaliert) |
| **QA / Tester** | AC → **Acceptance Tests** (Given/When/Then); Edge-/Error-Jagd → erzeugt Case-Kinder-Tickets |
| **Backend-Dev** | konsumiert **AC**; Datenmodell · API · BE-Tasks · Tech-Approach-Optionen · Größe |
| **Frontend-Dev** | UI/UX; klärt mit PO die **kürzesten Wege** durch die Prozesse; FE-Tasks · Größe |
| **Compliance** | prüft Tickets & fachliche Themen auf **DSGVO** & andere rechtliche Themen → Findings blocken DoR/DoD |
| **Security** | definiert **sicherheitsrelevante Tests** (parallel zu QAs funktionalen Tests) |

**AC = die Schnittstelle QA ↔ Backend** (der geteilte Vertrag; beide referenzieren dasselbe AC-Artefakt; bei Mehrdeutigkeit → zurück an PO).

## Ticket-Struktur & Slicing
- **Haupt-Ticket = ein ganzer Prozess-Schritt.**
- **Subtasks** bilden ab: **Happy Path · Edge Case · Error Case** (einzelne Fallentscheidungen) **+ technische Komponenten + Test-Komponenten** — je klein.
- **QA identifiziert** die Edge-/Error-Fälle beim AC-Härtetest → daraus werden die Case-Subtasks (nur *reale* Fälle, keine spekulativen).
- **Split-Regel (Ticket-Größe):** Braucht die *fachliche* Beschreibung ein **UND** oder **ODER**, um sie auszudrücken → **zwei Haupt-Tickets.** (Technische & Test-Anteile bleiben Subtasks.)
- **Happy Path zuerst** (trägt den Wert); Feature-**DoD erst erfüllt, wenn der Fall-Satz vollständig** ist.

## Protokoll / Fluss
```
Roadmap-Now
   │   ┌──────────── ORCHESTRATOR ────────────┐
   ▼   │ routet · Loop · Gates · Eskalation     │
       └────────────────────────────────────────┘
 1) PO   → Ticket-Scheiben (Happy) + AC          🚪 GATE: AC ok?
             └ fachliche Frage? → PO antwortet (ableitbar)
                                  └ sonst → 🚪 ESKALATION an Mensch
 2) AC ── Vertrag ──┬── QA: Acceptance Tests + Edge/Error-Kinder   🚪 GATE: Tests ok?
                    │        ↺ AC untestbar → zurück an PO
                    └── BE: Datenmodell/API/Tasks + Tech-Approach   🚪 DESIGN-GATE: Mensch
                             ↺ AC nicht machbar → zurück an PO
 3) FE   → UI/UX + kürzeste Wege (mit PO)
 4) DoR-Check ───────────────────────────────────🚪 GATE: Ticket REIF
                                                     ▼  Reifes Ticket
```

## Human-Gates
1. **AC** — Schnitt & Akzeptanzkriterien freigegeben.
2. **Acceptance Tests** — fangen die echte Absicht.
3. **Design / Tech-Approach** — der Mensch entscheidet (kein Architect-Agent).
4. **DoR / Reif** — Ticket ist umsetzbar.
Plus **Eskalations-Gate**: nicht-ableitbare Produktentscheidungen.

## Loop-Leitplanken
- **Max. 2 Rückspiel-Runden pro *Fragestellung*** — verschiedene Fragen/Themen bekommen je eigenes Budget.
- **Gesamt-Obergrenze pro Ticket** (Sicherung gegen „endlos neue Fragen") — dann Zwangs-Eskalation.
- **Konflikt** (z. B. QA „untestbar" vs. PO „doch") → **Mensch** entscheidet, nicht der lautere Agent.
- Orchestrator **fasst an jedem Gate das Offene zusammen** — schnelle Entscheidung statt Blindflug.

## Reifes-Ticket-Artefakt
`Intent/Wert · AC · Acceptance Tests · BE-Tasks · FE-Tasks/UX · Tech-Approach-Notiz · DoD-Referenz · Größe · Parent/Case-Typ`
+ **DoR-Checkliste** als Gate (siehe [`READY-TICKET-TEMPLATE.md`](./READY-TICKET-TEMPLATE.md)).

## Cross-cutting-Agenten & NFRs
- **Compliance-Agent:** DSGVO & rechtliche Themen → Findings blocken DoR/DoD.
- **Security-Agent:** definiert sicherheitsrelevante Tests → müssen grün sein.
- **Performance (stehendes NFR):** **von QA vorgegeben** (messbare Ziele), von **BE + FE** umzusetzen.
- **Delivery-Scans (bei Abnahme/Review, Teil der DoD):** Dependency-Scan · Container-Scan · SonarQube-Quality-Gate · weitere.
- **Wann was:** Anforderungen (Security-Tests, Perf-Ziele, Compliance-Review) entstehen **im Refinement**; die **Scan-Ergebnisse** werden **beim Delivery/Review** geprüft.

> Allgemeingültige DoD (Delivery-„fertig") → [`./DEFINITION_OF_DONE.md`](./DEFINITION_OF_DONE.md).
