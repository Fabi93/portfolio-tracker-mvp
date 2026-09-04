# Refinement · Compliance & Security

> Ergebnis von `compliance` (Skill `check-compliance`) und `security` (Skill `define-security-tests`). Findings können DoR/DoD **blocken**. Kein verbindlicher Rechtsrat — Unsicheres wird eskaliert.

## Compliance (DSGVO)

**Kernfrage:** Sind Portfolio-Daten personenbezogen? → Sobald sie einem eingeloggten Kunden zugeordnet sind: **ja** (Finanzdaten, hohe Schutzwürdigkeit). Im MVP gibt es **kein Login** → aktuell kein Personenbezug, aber der Weg dahin ist vorgezeichnet.

| # | Thema | Finding / Anforderung | Status |
|---|---|---|---|
| C1 | Rechtsgrundlage | Sobald pro Nutzer gespeichert wird: Rechtsgrundlage klären (Vertrag/Einwilligung) | **eskaliert an Legal** |
| C2 | Datenminimierung | Für die MVP-Logik genügen ISIN/Menge/Kurs — **keine** Klarnamen o. Ä. erfassen | offen für MVP: erfüllt |
| C3 | Speicherdauer/Löschung | Löschkonzept nötig, sobald persistiert wird (Ausbaustufe) | offen (Future Scope) |
| C4 | Betroffenenrechte | Export/Löschung des eigenen Portfolios vorsehen (Design) | offen (Future Scope) |
| C5 | Kein PII in Logs/URLs | Portfolio-/Kursdaten **nicht** in Query-Strings oder Logs | Test-Vorgabe |

## Security — Bedrohungsbetrachtung & Test-Vorgaben

| # | Bedrohung | Anforderung / Test (muss grün sein) |
|---|---|---|
| S1 | **Input-Validierung** | ISIN-Format & numerische Grenzen serverseitig prüfen; ungültige Eingaben → `400`, keine Speicherung (deckt AC1.2–1.4 ab) |
| S2 | **Numerische Robustheit** | `BigDecimal`, kein `double`; sehr große Mengen/Kurse ohne Überlauf/Präzisionsverlust |
| S3 | **Injection** | keine dynamischen Queries aus Nutzereingaben (bei späterer DB: Parameter-Bindung) |
| S4 | **Multi-Tenant-Isolation** | *Sobald Auth existiert:* Nutzer A darf Portfolio von B nicht lesen/ändern — Isolationstest |
| S5 | **Rate-Limiting** | Schreibende Endpunkte gegen Missbrauch begrenzen (Future Scope, sobald öffentlich) |
| S6 | **Secrets** | keine Secrets im Repo/Log; echte Kursquelle später über sichere Konfiguration |
| S7 | **Audit** | sensible Aktionen (Anlegen/Löschen von Positionen) nachvollziehbar (Future Scope) |

## Für das MVP verbindlich (blockt DoD)
- **S1, S2** und **C2, C5** sind im MVP direkt umsetzbar/testbar und Teil der Acceptance-/Security-Tests.
- **C1, C3, C4, S4, S5, S7** sind an Multi-User/Persistenz gekoppelt → als Future-Scope-Anforderungen dokumentiert, **vor** deren Umsetzung erneut prüfen.
