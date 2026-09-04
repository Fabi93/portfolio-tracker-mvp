---
name: define-frontend-plan
description: Aus den Akzeptanzkriterien (AC) eines Tickets den FRONTEND-PLAN ableiten — geht bewusst TIEF auf UI/UX: Informationsarchitektur & Screen-Trennung (eine Aufgabe pro View), Nutzerfluss/Navigation, kürzeste Wege, Zustände (empty/loading/error/success), Barrierefreiheit, mobile Ergonomie, visuelle Hierarchie & Konsistenz, Feedback — plus Screens/Komponenten, Frontend-Tasks, Interaktions-Optionen (Design-Gate), UI-Performance. Der Frontend-Dev-Anteil im Refinement. PLANUNG, kein Code.
---

# Skill: Frontend-Plan (Refinement)

## Was das ist — und was nicht
Aus den **AC** wird ein **Frontend-Plan** mit Fokus **UI/UX**. **Refinement, nicht Implementierung** — Plan, **kein Code**.
- Kernaufgabe: die **kürzesten Wege** durch den Prozess (wenig Klicks/Schritte) — **gemeinsam mit dem PO** geklärt.

## Voraussetzung
Ein Ticket mit **freigegebenen AC** (Skill `define-tickets`).

## Prozess
1. **AC laden.** Widersprüchlich/unklar für die UX → **Bounce an PO**.
2. **UX-Tiefe (Pflicht — nicht nur „kürzester Weg"):**
   - **Informationsarchitektur & Screen-Trennung:** *eine Aufgabe pro View* — verschiedene Aufgaben (z. B. *Liste ansehen* vs. *neu anlegen*) **bewusst trennen**, nicht alles auf einen Screen quetschen.
   - **Nutzerfluss & Navigation:** kürzeste Wege, **eine klare primäre Aktion je Screen**, Zurück-/Erfolgs-Navigation (wohin nach dem Speichern?).
   - **Barrierefreiheit (a11y):** Labels, Fokus-Reihenfolge, Kontrast, Screenreader, **Touch-Ziele ≥ 44 px**.
   - **Mobile-Ergonomie:** Daumenreichweite, primäre Aktion gut erreichbar (FAB/Bottom), kein horizontales Scrollen.
   - **Visuelle Hierarchie & Konsistenz:** klare Hierarchie, wiederverwendete Muster/Komponenten, konsistente Begriffe.
   - **Feedback & kognitive Last:** Rückmeldung nach *jeder* Aktion (Toast/Spinner/Erfolg); Felder/Optionen reduzieren; sinnvolle Defaults.
3. **Screens/Komponenten:** welche Views/Bausteine, welche States (leer/lädt/Fehler/erfolgreich) je Screen.
4. **Frontend-Tasks:** klein geschnitten.
5. **Interaktions-/Design-Optionen:** Varianten mit Tradeoffs → **Design-Gate** (Mensch entscheidet).
6. **Performance im UI:** Rendering/Ladezeiten gemäß QA-Zielen — auch bei großen Listen (Virtualisierung, Pagination).

## Anti-Patterns
- **Alles auf einen Screen quetschen** — Aufgaben nicht getrennt (fehlende Informationsarchitektur).
- **Lange Klickwege** statt kürzester Pfad.
- **Empty/Loading/Error-States vergessen** (koppelt an die Edge/Error-Subtasks).
- **Barrierefreiheit ignoriert** (Labels/Kontrast/Touch-Ziele/Fokus).
- **Kein Feedback** nach Aktionen (Nutzer weiß nicht, ob's geklappt hat).
- **Schon Code** statt Plan · **UX-Absicht erfunden** statt mit PO klären.

## Output-Format
```
# Frontend-Plan: [Ticket]
## Informationsarchitektur (Screen-Trennung)
- Views: … (je *eine* Aufgabe pro View) · Navigation dazwischen · primäre Aktion je Screen
## UX-Flow (kürzester Weg)
- Schritt 1 → 2 → 3 (min. Klicks) · wohin nach Erfolg?
## Screens / Komponenten / States
- View … (leer / lädt / Fehler / erfolgreich) · a11y (Labels/Fokus/Kontrast/≥44px) · mobile Ergonomie
## Frontend-Tasks
- [ ] …
## Interaktions-Optionen (Entscheidung: Mensch)
- Option A / Option B (Tradeoff)
## Performance-Beachtung
- große Listen: … (Virtualisierung/Pagination)
## Bounces an PO
- …
```

## Quellen
UX-Heuristiken (kürzeste Wege / minimale Klicktiefe); UI-States (empty/loading/error/success); Frontend-Performance-Budgets.
