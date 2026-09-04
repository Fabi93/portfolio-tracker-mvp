# Allgemeingültige Definition of Done (DoD)

_Status: **v1 abgestimmt** · 2026-08-30 · wiederverwendbar/portabel (in Projekte kopierbar)._
_Referenziert von jedem reifen Ticket (siehe `REFINEMENT.md`). **Allgemeine DoD ≠ ticket-spezifische Akzeptanzkriterien** — beide gelten._

Ein Ticket ist **fertig**, wenn:

## A — Verifikation
1. **Scope-Treue:** exakt der Ticket-Schritt umgesetzt — keine ungefragten Extras, kein Scope-Creep.
2. **Akzeptanzkriterien erfüllt & nachweislich geprüft** (nicht angenommen).
3. **Mindestens 1 Acceptance Test** grün; Happy Path + alle als Subtasks identifizierten **Edge-/Error-Fälle** abgedeckt.
4. **Baut & läuft:** kompiliert sauber, startet, keine kaputten Imports.

## B — Qualität / Anti-Slop
5. **Keine Slop-Marker:** keine erfundenen APIs/Dependencies, kein toter Code, keine TODO-/Platzhalter-Reste.
6. **Konventionen:** folgt bestehendem Stil/Struktur; **kein neuer Stack/Lib ohne bewusste Entscheidung**.
7. **Reviewbar:** Diff klein genug, um es in einem Sitzen zu prüfen (vgl. Split-Regel: fachliches UND/ODER → zwei Tickets).
8. **Clean Code / Clean Architecture** eingehalten.

## C — Nicht-funktionale Anforderungen (NFR)
9. **Security-Tests grün** (vom Security-Agent definiert).
10. **Performance-Ziele erfüllt** (von QA vorgegeben, von BE+FE umgesetzt): echtzeit-nah, kurze Ladezeiten **auch bei großen Datenmengen**.
11. **Compliance/DSGVO** ok (vom Compliance-Agent geprüft) — keine offenen rechtlichen Findings.
12. **Keine Secrets** im Code; nötige Doku vorhanden.

## D — Delivery-Scans (bei Abnahme/Review)
13. **Dependency-Scan** ohne kritische Findings.
14. **Container-Scan** ohne kritische Findings.
15. **SonarQube Quality-Gate** grün.
16. Weitere projektspezifische Scans grün.

---

**Refinement-Zeit vs. Delivery-Zeit:** Die *Anforderungen* (Security-Tests, Perf-Ziele, Compliance-Review, AC) entstehen im **Refinement**. Die *Nachweise* (Tests grün, Scans grün) werden beim **Delivery/Review** geprüft.

**Kern-Tore (v1):** min. 1 Acceptance Test · Split bei fachlichem UND/ODER · Security + Compliance + Performance + Scans als Pflicht-Tore.
