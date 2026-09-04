---
name: run-scans
description: DELIVERY (Review) — der Security-Anteil in der Umsetzung. Führt die Scans am fertig implementierten, grünen Ticket aus: SonarQube (Container starten, Code-Scan), Dependency-Scan, Image-/Container-Scan und im Frontend npm audit. Sammelt die ECHTEN Werte (keine erfundenen), begleitet die 2 Review-Iterationen und liefert die Zahlen + offenen Punkte für die MR-Description. Führt die Scans AUS (im Gegensatz zum Refinement, wo Security nur Tests definiert).
---

# Skill: Scans ausführen (Delivery, Review)

## Was das ist
Am **fertig implementierten, grün getesteten** Ticket die **Sicherheits-/Qualitäts-Scans ausführen** und die **echten Werte** einsammeln. Anders als im Refinement (dort *definiert* Security nur Tests) werden hier die Scans **real gefahren**. Ergebnis speist die **MR-Description**.

## Voraussetzung
Implementierung fertig, alle Acceptance-/Unit-/Integrationstests grün, Build läuft.

## Prozess
1. **SonarQube:** Container starten (`docker run` sonarqube), Projekt scannen (Scanner/Gradle-Plugin). Werte holen: **Bugs, Vulnerabilities, Security Hotspots, Code Smells, Coverage, Duplication, Quality-Gate-Status**.
2. **Dependency-Scan** (Backend & Frontend): bekannte CVEs in Abhängigkeiten (z. B. OWASP Dependency-Check / `gradle dependencyCheck`, `npm audit`). Werte: Anzahl je Schweregrad (critical/high/medium/low).
3. **Image-/Container-Scan:** das gebaute Container-Image scannen (z. B. Trivy). Werte: Findings je Schweregrad, betroffene Pakete.
4. **Frontend `npm audit`:** Ausgabe je Schweregrad; ob `npm audit fix` gefahrlos möglich.
5. **Echte Werte festhalten** — niemals schätzen/erfinden. Scan nicht ausführbar? → als **„nicht ausgeführt: Grund"** dokumentieren, nicht faken.
6. **2 Review-Iterationen begleiten:** Nach Runde 1 die kritischen Findings an BE/FE zum Fixen geben; Runde 2 erneut scannen. **Genau zweimal** — was danach offen ist, wird **nicht** endlos weiter iteriert, sondern **transparent in die MR-Description** geschrieben (mit Schweregrad + Begründung/Plan).
7. **Übergabe an den Orchestrator:** strukturierte Werte + Rest-Findings für den PR-Text.

## Anti-Patterns
- **Werte erfinden/schätzen** statt echt scannen.
- **Scan „grün" behaupten**, obwohl er nicht lief.
- **Mehr als 2 Iterationen** (Scope-Creep) — oder offene Punkte verschweigen.
- **Nur Sonar**, Dependency/Image/npm audit auslassen.
- **Secrets** in Scan-Logs/PR-Text durchreichen.

## Output-Format
```
# Scan-Report: [Ticket] — Iteration {1|2}
## SonarQube
- Quality Gate: PASS/FAIL · Bugs: n · Vulns: n · Hotspots: n · Smells: n · Coverage: x% · Duplication: x%
## Dependency-Scan (BE / FE)
- critical: n · high: n · medium: n · low: n  (Top-Findings: …)
## Image-/Container-Scan
- critical: n · high: n · … (Image: …, betroffene Pakete: …)
## npm audit (Frontend)
- critical: n · high: n · moderate: n · low: n · fixbar: ja/nein
## Nach 2 Iterationen noch OFFEN (→ MR-Description)
- [Schweregrad] Finding … · Grund/Plan …
## Nicht ausgeführt
- Scan … : Grund …
```

## Quellen
SonarQube (Quality Gate/Metriken); OWASP Dependency-Check; Trivy (Image-Scan); `npm audit`. Werte sind **gemessen**, nicht behauptet („demonstrated, not claimed").
