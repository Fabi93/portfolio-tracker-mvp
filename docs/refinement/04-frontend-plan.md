# Refinement · Frontend-Plan (Frontend-Developer)

> Ergebnis von `frontend-developer` / Skill `define-frontend-plan`. **Planung, kein Code.** Fokus: kürzeste Wege, klare States. Umsetzung (Angular) live in der Delivery.

## Informationsarchitektur (eine Aufgabe pro View)

1. **Portfolio-Übersicht** (Landing) — beantwortet „Wie steht mein Portfolio?" auf einen Blick:
   - Kopf: **Gesamtwert** (US2) groß, darunter **G/V absolut** (US3) mit Vorzeichen/Farbe.
   - Liste der Positionen (ISIN, Stückzahl, Kaufkurs, aktueller Kurs, Positionswert).
   - Primäraktion: „Position hinzufügen".
2. **Position hinzufügen** — ein schlankes Formular (ISIN, Stückzahl, Kaufkurs) mit Inline-Validierung.

> Kürzester Weg: Übersicht → „+ Position" → Formular → speichern → zurück zur Übersicht mit aktualisierten Kennzahlen. Kein Zwischenschritt, keine Modalkette.

## Zustände je View (empty / loading / error / success)

| View | empty | loading | error | success |
|---|---|---|---|---|
| Übersicht | „Noch keine Position — jetzt hinzufügen" (CTA) | Skeleton für Kennzahlen/Liste | Fehlerbanner + Retry | Kennzahlen + Liste |
| Formular | – | Button „Speichern…" disabled | Feldfehler inline (US1-Validierung) | Toast + Rücksprung |

## Komponenten (grob)
- `PortfolioSummary` (Gesamtwert + G/V), `PositionList` / `PositionRow`, `AddPositionForm`, `PortfolioService` (HTTP-Client gegen die REST-API).

## Barrierefreiheit & Ergonomie
- Beträge mit Vorzeichen **und** Farbe (nicht nur Farbe) → G/V für Farbfehlsichtige erkennbar.
- Formular: `label`-Verknüpfung, Fehlermeldungen mit `aria-describedby`, Fokus auf erstes Fehlerfeld.
- Zahlen-/Währungsformat via `Intl`/Angular-Pipes, EUR, 2 Nachkommastellen.
- Mobile-first: eine Spalte, Kennzahlen oben, Aktion daumenerreichbar.

## Interaktions-Optionen (Design-Gate)
- **Position-Anlage:** eigener Screen *(empfohlen, klarster Fokus)* vs. Inline-/Slide-over-Form auf der Übersicht.
- **Aktualisierung nach Speichern:** Vollreload der Kennzahlen vs. optimistisches Update.

## UI-Performance
- Kennzahlen erst rendern, wenn Wert **und** G/V vorliegen (kein Zahlensprung); Liste virtualisiert erst bei > ~100 Positionen relevant (Future Scope).
