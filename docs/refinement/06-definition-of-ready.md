# Refinement · Definition of Ready (Human-Gate)

> Der Abschluss von Stufe 1. Ein Ticket ist „ready", wenn alle Punkte erfüllt oder bewusst als Future Scope markiert sind. **Freigabe ist eine menschliche Entscheidung.**

## Checkliste je Ticket (US1–US3)

- [x] User Story klar, Nutzen benannt
- [x] Akzeptanzkriterien testbar formuliert (`01-tickets.md`)
- [x] Acceptance-Test-Specs vorhanden (`02-acceptance-tests.md`) + als `@Disabled`-Tests hinterlegt
- [x] Backend-Plan inkl. Tech-Optionen (`03-backend-plan.md`)
- [x] Frontend-Plan inkl. States & kürzeste Wege (`04-frontend-plan.md`)
- [x] Compliance/Security geprüft, Findings dokumentiert (`05-compliance-security.md`)
- [x] Offene fachliche Punkte explizit gemacht (nicht geraten)
- [x] Größe geschätzt (S)

## Offene Entscheidungen für das Design-Gate im Workshop
1. **Persistenz:** In-Memory (MVP) bestätigen oder direkt JPA/Postgres? → `03-backend-plan.md`
2. **Gleiche ISIN mehrfach:** separate Positionen (MVP) bestätigen? → `01-tickets.md`
3. **Position-Anlage-UX:** eigener Screen vs. Inline-Form? → `04-frontend-plan.md`

> Sind diese drei entschieden, ist US1–US3 **ready for delivery**. Genau hier steigt der Workshop ein.
