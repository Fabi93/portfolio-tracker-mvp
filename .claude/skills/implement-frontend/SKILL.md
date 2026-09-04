---
name: implement-frontend
description: DELIVERY — der Frontend-Developer-Anteil in der Umsetzung. Gemeinsam mit dem Backend-Dev den API-Kontrakt (openapi.yaml) erstellen, dann das Frontend DAGEGEN und gegen die Playwright-Acceptance-Tests von QA implementieren — inkl. eigener Unit- (Komponenten-) und Integrationstests. Setzt den im Refinement geplanten UX-Flow um (kürzeste Wege, States, a11y, mobile Ergonomie). Arbeitet strikt nach Clean Code (Robert C. Martin). Läuft PARALLEL zum Backend. Echter Code.
---

# Skill: Frontend implementieren (Delivery)

## Was das ist
Das Frontend eines reifen Tickets **umsetzen** — gegen den **API-Kontrakt** und die **Playwright-Acceptance-Tests** (QA, test-first, bereits rot), entlang des im Refinement geplanten **UX-Flows**. Echter, getesteter, sauberer Code. Läuft **parallel** zum Backend; der Kontrakt ist die gemeinsame Schnittstelle.

## Voraussetzung
Reifes Ticket + **Frontend-Plan** (define-frontend-plan) + **QA-Playwright-Acceptance-Tests** (rot) inkl. Compliance-/Security-Abdeckung.

## Prozess
1. **API-Kontrakt (gemeinsam mit BE):** `openapi.yaml` — bindend. FE konsumiert exakt die dort definierten Requests/Responses/Fehlermodelle; Typen möglichst aus dem Kontrakt ableiten statt handzuschreiben.
2. **Test-first respektieren:** Die Playwright-Acceptance-Tests laufen zuerst rot; implementieren bis grün — Tests nicht anpassen, um grün zu werden.
3. **UX-Flow umsetzen** (aus dem Frontend-Plan): **kürzeste Wege**, *eine* Aufgabe pro View (Screen-Trennung), klare primäre Aktion, Erfolgs-/Zurück-Navigation.
4. **Zustände vollständig:** **empty / loading / error / success** je Screen — nicht nur den Happy-Flow. Nutzer-**Feedback** nach jeder Aktion (Toast/Spinner/Fehlermeldung).
5. **Barrierefreiheit & mobile Ergonomie:** Labels/Rollen (auch für stabile Playwright-Selektoren), Fokus-Reihenfolge, Kontrast, Touch-Ziele ≥ 44 px, Daumenreichweite, kein horizontales Scrollen.
6. **Eigene Tests schreiben:**
   - **Unit-/Komponententests:** Komponentenlogik, Formvalidierung, State-Übergänge (empty/loading/error/success) isoliert.
   - **Integrationstests:** Zusammenspiel Komponente ↔ Service ↔ HTTP (gemockter Kontrakt), inkl. Fehlerpfade.
   - **Playwright-E2E (QA):** müssen grün werden — der Nutzerfluss end-to-end.
7. **Compliance & Security im FE:** keine sensiblen Daten in URL/Logs, Eingabe-Validierung clientseitig (zusätzlich, nicht statt Server), Einwilligung/Datensparsamkeit wie definiert — zugehörige Acceptance-Tests grün.
8. **Clean Code (R. C. Martin)** durchgehend — siehe Checkliste. **UI-Performance** gegen QA-Ziele (Renderzeit, große Listen: Virtualisierung/Pagination).

## Clean-Code-Checkliste (verbindlich)
- **Namen** absichtsoffenbarend; **Funktionen/Komponenten** klein, eine Aufgabe, ein Abstraktionsniveau, wenige Props/Args, keine versteckten Seiteneffekte.
- **Fehler** explizit behandeln (kein stiller Fehlerzustand); kein toter/auskommentierter Code.
- **Kommentare** nur wo nötig; **DRY** (wiederverwendete Komponenten/Muster); Boundaries (HTTP/SDK) kapseln.
- **Trennung** von Darstellung und Logik (Service/State getrennt von View).
- **Tests = First-Class:** F.I.R.S.T.; stabile, rollenbasierte Selektoren; keine brüchigen Timing-/Pixel-Abhängigkeiten.

## Anti-Patterns
- **Acceptance-Tests anpassen**, um grün zu werden.
- **Kontrakt umgehen** / eigene abweichende Typen erfinden.
- **States vergessen** (nur Happy Flow), **kein Feedback** nach Aktionen.
- **a11y ignoriert** (Labels/Kontrast/Touch-Ziele) → auch brüchige Selektoren.
- **Alles auf einen Screen** statt Aufgaben-Trennung.
- **Sensible Daten in URL/Logs.**

## Output-Format
```
# Frontend-Implementierung: [Ticket]
## Kontrakt (openapi.yaml)
- konsumierte Endpunkte/Typen … (mit BE abgestimmt: ✅)
## Umgesetzt
- Views/Komponenten/Services … · States je View (empty/loading/error/success)
## Tests
- Unit/Komponente: … · Integration: … · Playwright-E2E (QA): grün ✅
## a11y / Mobile
- Labels/Fokus/Kontrast/≥44px/Daumenreichweite …
## Compliance/Security umgesetzt
- … → Tests grün
## Clean-Code-Notizen
- …
## Performance
- QA-Ziel … → gemessen …
## Offen / Risiken
- …
```

## Quellen
Clean Code (R. C. Martin); Playwright (rollenbasierte Selektoren, Web-First-Assertions); UI-States (empty/loading/error/success); a11y (WCAG-Grundlagen, Touch-Ziele).
