---
name: define-backend-plan
description: Aus den Akzeptanzkriterien (AC) eines Tickets den BACKEND-PLAN ableiten — Datenmodell/Schema, API-Kontrakt, Backend-Tasks, Tech-Approach-OPTIONEN (Entscheidung am Human-Design-Gate), Risiken/Unbekannte, Größe. Der Backend-Dev-Anteil im Refinement. PLANUNG, kein Code. Wählt den Ansatz NICHT selbst — legt Optionen vor. Bounce an PO, wenn AC nicht machbar.
---

# Skill: Backend-Plan (Refinement)

## Was das ist — und was nicht
Aus den **AC** (dem Vertrag) wird ein **umsetzbarer Backend-Plan**. **Refinement, nicht Implementierung** — es entsteht ein Plan, **kein Code**.
- **Wählt den Tech-Approach NICHT selbst** — legt Optionen + Tradeoffs vor; die Entscheidung fällt am **Human-Design-Gate**.

## Voraussetzung
Ein Ticket mit **freigegebenen AC** (Skill `define-tickets`).

## Prozess
1. **AC laden** (Vertrag). Nicht machbar / widersprüchlich → **Bounce an PO**.
2. **Datenmodell/Schema:** neue/geänderte Entitäten, Felder, Migrationen.
3. **API-Kontrakt:** Endpunkte, Request/Response, Statuscodes, Fehlerformate.
4. **Backend-Tasks:** klein geschnitten, je einzeln umsetzbar/prüfbar.
5. **Tech-Approach-Optionen:** 2–3 Ansätze mit Tradeoffs → **zur Entscheidung vorlegen**, nicht selbst festlegen.
6. **Risiken/Unbekannte** benennen (Performance, Multi-Tenancy, Migration…).
7. **Performance-Ziele** von QA berücksichtigen (Antwortzeiten bei großen Datenmengen).

## Anti-Patterns
- **Ansatz still selbst gewählt** (reißt die 1.2-Lücke auf).
- **Schon Code** statt Plan.
- **Tasks zu grob** / nicht einzeln prüfbar.
- **AC stillschweigend interpretiert** statt Bounce.

## Output-Format
```
# Backend-Plan: [Ticket]
## Datenmodell / Schema
- …
## API-Kontrakt
- METHODE /pfad → Request/Response/Fehler
## Backend-Tasks
- [ ] …
## Tech-Approach-Optionen (Entscheidung: Mensch)
- Option A … (Tradeoff) / Option B … (Tradeoff)
## Risiken / Unbekannte
- …
## Bounces an PO
- …
```

## Quellen
Vertikale Slices; API-Design (REST/Contracts); Trennung Plan ↔ Implementierung (Refinement vs. Delivery).
