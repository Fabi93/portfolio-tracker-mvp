---
name: define-tickets
description: Ein Roadmap-Item in reife TICKET-Kandidaten schneiden — Haupt-Tickets (je ein ganzer Prozess-Schritt) mit testbaren Akzeptanzkriterien (AC) und Subtask-Struktur (Happy Path + technische + Test-Komponenten). Der PO-Anteil im Refinement. Einsetzen, wenn aus einem freigegebenen Roadmap-„Now" konkrete Tickets werden sollen. Sokratisch/extraktiv — AC ist der Vertrag für QA & Backend; erfindet keine fachliche Absicht, sondern eskaliert; Human-Gate.
---

# Skill: Tickets schneiden & Akzeptanzkriterien

## Was das ist — und was nicht
Aus einem Roadmap-Item werden **Haupt-Tickets** mit **Akzeptanzkriterien (AC)**. Die AC sind der **Vertrag**, den QA (Acceptance Tests) und Backend (Implementierung) beide konsumieren.
- **Kein** Schreiben von Acceptance Tests (das macht QA), **keine** technische Umsetzung/Architektur (Devs), **kein** Code.

## Voraussetzung
Ein **freigegebenes Roadmap-„Now"-Item** (Skill `define-roadmap`). Fehlt es, zuerst dorthin.

## Prozess (sokratisch — extrahieren, nicht erfinden)

### 1 — In Haupt-Tickets schneiden
- **Ein Haupt-Ticket = ein ganzer Prozess-Schritt.**
- **Split-Regel (Größe):** Braucht die *fachliche* Beschreibung ein **UND** oder **ODER**, um sie auszudrücken → **zwei Haupt-Tickets.** Nicht künstlich zusammenfassen.
- Jedes Ticket **vertikal & lieferbar** (INVEST: independent, valuable, testable).

### 2 — Akzeptanzkriterien (AC) je Ticket
- **Testbar, eindeutig, in Nutzer-/Fachsprache.** Was ist wahr, wenn der Schritt korrekt getan ist?
- Die AC sind der **Vertrag** — präzise genug, dass QA daraus Tests und Backend daraus Verhalten ableiten kann.
- Mehrdeutigkeit ist ein Defekt: unklare AC = noch nicht reif.

### 3 — Subtask-Gerüst anlegen
- **Happy Path** (der Standardpfad) · **technische Komponenten** · **Test-Komponenten**.
- **Edge-/Error-Fälle** werden vom **QA-Agenten** ergänzt (dessen Härtetest erzeugt sie) — hier nur der Platz dafür.

### 4 — Fachliche Lücken NICHT erfinden
Fehlt eine echte, noch nicht getroffene Produktentscheidung → **an den Menschen eskalieren** (nicht raten). Ableitbares aus Vision/Strategie/Roadmap darf beantwortet werden.

### 5 — Human-Gate
Ticket-Schnitt + AC vorlegen, **plus** ehrliche Notiz, wo AC noch schwammig, zu groß (UND/ODER) oder in Wahrheit mehrere Schritte sind. Erst nach Freigabe „AC ok".

## Anti-Patterns
- **AC nicht testbar** („soll gut funktionieren").
- **Haupt-Ticket zu groß** — UND/ODER überlesen.
- **Technische Tasks als Haupt-Tickets** statt als Subtasks.
- **Fachliche Absicht erfunden** statt eskaliert.

## Output-Format
```
# Ticket: [Prozess-Schritt]
## Intent & Wert
- Warum / welcher Nutzen?
## Akzeptanzkriterien (AC — Vertrag)
- [ ] …
- [ ] …
## Subtasks
- Happy Path: …
- (Edge/Error: von QA ergänzt)
- Technische Komponenten: …
- Test-Komponenten: …
## Offene Eskalationen (an Mensch)
- …
## Ehrliche Einschätzung
- Wo AC/Schnitt noch wackeln — brutal ehrlich.
```

## Quellen
INVEST-Kriterien (Bill Wake); Akzeptanzkriterien in Nutzer-/Fachsprache; Vorbereitung auf Gherkin/Given-When-Then (QA-Stufe).
