---
name: check-compliance
description: Ein Ticket / fachliches Thema auf DSGVO und andere rechtlich relevante Themen prüfen — personenbezogene Daten, Rechtsgrundlage, Datenminimierung, Speicherdauer/Löschung, Betroffenenrechte (Auskunft/Export/Löschung), Einwilligung, Auftragsverarbeitung. Der Compliance-Anteil im Refinement. Erzeugt Findings, die DoR/DoD blocken. Täuscht KEINEN Rechtsrat vor — Unsicheres wird an den Menschen eskaliert.
---

# Skill: Compliance-Check (DSGVO & Recht)

## Was das ist — und was nicht
Prüft Tickets/AC/fachliche Themen auf **rechtliche Relevanz**, v. a. **DSGVO**. Erzeugt **Findings**, die die DoR/DoD **blocken**, bis geklärt.
- **Kein verbindlicher Rechtsrat.** Unsichere/echte Rechtsfragen → **Eskalation an den Menschen** (ggf. juristische Prüfung).

## Voraussetzung
Ein Ticket mit AC (oder ein fachliches Thema) zur Prüfung.

## Prozess — DSGVO-Checkliste
1. **Personenbezogene Daten?** Welche Felder sind personenbezogen/sensibel?
2. **Rechtsgrundlage** (Art. 6): Vertrag, Einwilligung, berechtigtes Interesse …?
3. **Datenminimierung:** wird nur das Nötige erhoben?
4. **Speicherdauer / Löschkonzept:** wie lange, wann gelöscht? (z. B. Austritt → Aufbewahrung vs. Löschung).
5. **Betroffenenrechte:** Auskunft, Export (Datenportabilität), Berichtigung, **Löschung**.
6. **Einwilligung** nötig & einholbar/widerrufbar?
7. **Auftragsverarbeitung / Drittdienste** (Hosting, Mail, Push): AV-Vertrag, EU-Hosting?
8. **Audit/Nachvollziehbarkeit** sicherheitsrelevanter Aktionen.

Dann: **andere rechtliche Themen** je Domäne (z. B. Minderjährige, Zahlungsdaten).

## Anti-Patterns
- **Rechtsrat vorgetäuscht** statt eskaliert.
- **Löschkonzept vergessen** (häufig bei „Kündigung/Historie").
- **Findings unkonkret** — nicht prüfbar/nicht blockierend formuliert.

## Output-Format
```
# Compliance-Findings: [Ticket]
## DSGVO
- [BLOCKER|Hinweis] Thema … → Anforderung/Frage …
## Andere rechtliche Themen
- …
## Eskalation an Mensch (echte Rechtsfrage)
- …
```

## Quellen
DSGVO (Art. 5 Grundsätze, Art. 6 Rechtsgrundlagen, Betroffenenrechte Art. 15–20); Privacy-by-Design. **Kein Ersatz für juristische Beratung.**
