---
name: implement-backend
description: DELIVERY — der Backend-Developer-Anteil in der Umsetzung. Gemeinsam mit dem Frontend-Dev den API-Kontrakt (openapi.yaml) erstellen, dann das Backend DAGEGEN und gegen die Acceptance Tests von QA implementieren — inkl. eigener Unit- und Integrationstests (Testcontainers für DB-/Infra-Abhängigkeiten). Arbeitet strikt nach Clean Code (Robert C. Martin) & Clean Architecture. Läuft PARALLEL zum Frontend. Echter Code.
---

# Skill: Backend implementieren (Delivery)

## Was das ist
Das Backend eines reifen Tickets **umsetzen** — gegen den **API-Kontrakt** und die **Acceptance Tests** (QA, test-first, bereits rot). Echter, getesteter, sauberer Code. Läuft **parallel** zum Frontend; der Kontrakt ist die gemeinsame Schnittstelle.

## Voraussetzung
Reifes Ticket + **QA-Acceptance-Tests** (rot) inkl. Compliance-/Security-Abdeckung.

## Prozess
1. **API-Kontrakt zuerst (gemeinsam mit FE):** `openapi.yaml` — Pfade, Methoden, Request/Response-Schemas, Fehlermodelle (Status + Error-Body), Validierungsregeln. **Bindend** für BE **und** FE. Deckt sich mit den Acceptance-Tests; Abweichung → mit QA/FE klären.
2. **Test-first respektieren:** Zuerst die Acceptance-Tests laufen lassen (rot), dann implementieren bis grün — nicht die Tests anpassen, um grün zu werden.
3. **Implementieren gegen den Kontrakt:** Controller/Endpunkte exakt nach `openapi.yaml`; Domänenlogik in der Mitte, Infrastruktur außen (**Clean Architecture** — Dependency Rule: nach innen zeigen).
4. **Eigene Tests schreiben:**
   - **Unit-Tests:** Domänen-/Service-Logik isoliert (schnell, ohne Infra), Happy + Edge + Error.
   - **Integrationstests:** echte Kollaboration (Repository/DB, Web-Layer). **Testcontainers** für DB-/Infra-Abhängigkeiten (echte Postgres statt Mock), damit gegen die reale Engine getestet wird.
   - **API-Tests:** Endpunkt-Ebene (Request→Status/Body), ergänzend zu den QA-Acceptance-Tests.
5. **Compliance & Security umsetzen:** Input-Validierung serverseitig, AuthZ, keine Datenexposition/Secrets im Response/Log, Datenminimierung/Löschung wie im Refinement definiert — die zugehörigen Acceptance-Tests müssen grün werden.
6. **Clean Code (R. C. Martin) durchgehend** — siehe Checkliste. **Performance** gegen die QA-Ziele prüfen (N+1, Indizes, Antwortzeit-Budget).
7. **Grün-Nachweis:** Alle Acceptance-, Unit-, Integrations- und API-Tests grün; Build läuft.

## Clean-Code-Checkliste (verbindlich)
- **Namen:** absichtsoffenbarend, keine Rätsel-Kürzel.
- **Funktionen:** klein, *eine* Aufgabe, ein Abstraktionsniveau, 0–2 Argumente, keine Flag-Args, keine versteckten Seiteneffekte, Command-Query-Separation.
- **Fehler:** Exceptions statt Fehlercodes; kein `null` zurückgeben/übergeben; try/catch isolieren; keine leeren Catches.
- **Kommentare:** nur wo Code es nicht sagen kann; kein toter/auskommentierter Code.
- **DRY:** keine Duplikation; Boundaries (externe Libs/DB) kapseln.
- **SOLID + Clean Architecture:** Abhängigkeiten zeigen nach innen; Domäne kennt keine Infrastruktur.
- **Tests = First-Class:** F.I.R.S.T.; ein Assert-Gedanke pro Test; Tests so sauber wie Produktivcode.

## Anti-Patterns
- **Acceptance-Tests anpassen**, um grün zu werden (statt Code fixen).
- **Kontrakt umgehen** / vom `openapi.yaml` abweichen ohne Abstimmung.
- **Nur Happy Path** testen; Edge/Error/Compliance/Security auslassen.
- **Mock-DB statt Testcontainers**, wo echte DB-Semantik zählt.
- **Riesenfunktionen / Anemic-God-Service**, Logik im Controller.
- **Secrets/PII in Logs oder Responses.**

## Output-Format
```
# Backend-Implementierung: [Ticket]
## Kontrakt (openapi.yaml)
- Endpunkte/Schemas … (mit FE abgestimmt: ✅)
## Umgesetzt
- Klassen/Schichten … (Domäne/Application/Infra)
## Tests
- Unit: … · Integration (Testcontainers): … · API: …
- Acceptance-Tests (QA): grün ✅
## Compliance/Security umgesetzt
- Validierung/AuthZ/Datenminimierung … → Tests grün
## Clean-Code-Notizen
- …
## Performance
- QA-Ziel … → gemessen …
## Offen / Risiken
- …
```

## Quellen
Clean Code & Clean Architecture (R. C. Martin); Testcontainers; Testpyramide (Unit > Integration > E2E); OpenAPI.
