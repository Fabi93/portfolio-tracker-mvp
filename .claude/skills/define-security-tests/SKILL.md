---
name: define-security-tests
description: Für ein Ticket die SICHERHEITSRELEVANTEN Tests und Anforderungen definieren — Bedrohungsbetrachtung (Auth/AuthZ, Injection, Datenexposition, Input-Validierung, Rate-Limiting, Secrets, Audit sensibler Aktionen) und daraus konkrete Security-Tests, die grün sein müssen. Der Security-Anteil im Refinement. Definiert Tests/Anforderungen — führt KEINE Scans aus (die gehören ins Delivery/Review).
---

# Skill: Security-Tests (Refinement)

## Was das ist — und was nicht
Definiert die **sicherheitsrelevanten Tests & Anforderungen** eines Tickets. Diese müssen später **grün** sein (Teil der DoD).
- **Führt keine Scans aus** (Dependency/Container/SonarQube) — das ist **Delivery/Review**. Hier entstehen die **Anforderungen**.

## Voraussetzung
Ein Ticket mit AC (Skill `define-tickets`).

## Prozess — Bedrohungsbetrachtung
1. **AuthN/AuthZ:** wer darf diese Aktion? Multi-Tenant-Isolation (Zugriff nur auf eigene Daten / den eigenen Mandanten)?
2. **Input-Validierung / Injection:** SQL/NoSQL/Command-Injection, XSS, Massenzuweisung.
3. **Datenexposition:** werden zu viele Felder ausgegeben? Sensible Daten geloggt?
4. **Rate-Limiting / Missbrauch** sensibler Endpunkte.
5. **Secrets:** keine Secrets im Code/Client.
6. **Audit:** sicherheitsrelevante Aktionen nachvollziehbar (koppelt an DSGVO-Audit).

Daraus je einen **Security-Test** (Given/When/Then) ableiten, der den Missbrauch **negativ** prüft.

## Anti-Patterns
- **Nur Happy-Path-Security** (kein „unautorisiert → 403"-Test).
- **Tenant-Isolation ungetestet.**
- **Scans mit Tests verwechselt** (Scans = Delivery).

## Output-Format
```
# Security-Tests: [Ticket]
## Bedrohungen
- …
## Security-Tests (müssen grün sein)
- [AuthZ] Given fremder Tenant / When Zugriff / Then 403
- [Injection] …
## Anforderungen (in AC/DoD)
- …
```

## Quellen
OWASP Top 10 / OWASP ASVS; Negative Testing; Multi-Tenant-Isolation. Scans (SCA/Container/SAST) = Delivery-Gate, nicht hier.
