# Minimal Portfolio Tracker — Workshop-Vorbereitung

Ein B2C-Feature-MVP für ein Fintech: Endkunden erfassen ihr Portfolio **manuell** (bestandsbasiert) und sehen **Gesamtwert** und **Gewinn/Verlust** auf einen Blick (inspiriert von *justETF*).

Dieses Repo ist die **Hausaufgabe für den gemeinsamen Workshop**. Es zeigt ein **agentisches Setup** in Claude Code, mit dem aus einem PRD reife Stories und echte Implementierung entstehen. Der Fokus des Workshops liegt auf der **Live-Umsetzung** und der **gemeinsamen technischen Bewertung** — deshalb ist der lese-lastige Teil (Refinement) hier bereits vorbereitet.

## Was ist schon fertig (Hausaufgabe)

- 📄 **PRD** → [`docs/PRD.md`](docs/PRD.md)
- 🤖 **Agentisches Setup erklärt** → [`docs/agentic-setup.md`](docs/agentic-setup.md) (Pipeline-Diagramm, Rollen, Human-Gates)
- ✅ **Refinement für US1–US3** (Stufe 1) → [`docs/refinement/`](docs/refinement/) — Tickets + Akzeptanzkriterien, Acceptance-Test-Specs, Backend-/Frontend-Plan, Compliance & Security, Definition of Ready
- 🌱 **Buildbarer Seed:** Spring-Boot-Backend (Clean Architecture, Domänen-Logik als TODO für die Live-Delivery) + Angular-Frontend-Scaffold
- 🧪 **Test-First:** die Acceptance-Tests für US2/US3 liegen als `@Disabled`-JUnit-Tests bereit — im Workshop aktivieren (rot) und grün implementieren

## Repo-Struktur

```
.
├── .claude/            # das agentische Setup (Agents + Skills) — reist mit dem Repo
│   ├── agents/         # product-owner, qa-tester, backend-/frontend-developer, compliance, security, orchestrators
│   └── skills/         # define-tickets, write-acceptance-tests, implement-backend/-frontend, run-scans, …
├── docs/
│   ├── PRD.md
│   ├── agentic-setup.md
│   └── refinement/     # Stufe-1-Ergebnisse für US1–US3
├── backend/            # Spring Boot 3 / Java 21 (Maven)
└── frontend/           # Angular 22
```

## Quickstart

### Backend (Java 21 + Maven)

> Wichtig: Maven muss mit **JDK 21** laufen. Auf diesem Rechner zeigt `mvn` sonst auf ein älteres JDK.

```bash
export JAVA_HOME="$(/usr/libexec/java_home -v 21)"
cd backend
mvn test          # PositionTest ist grün; Acceptance-Tests sind @Disabled (Test-First)
mvn spring-boot:run
```

### Frontend (Angular 22 / Node ≥ 20)

> Das mitgelieferte `frontend/.npmrc` setzt `legacy-peer-deps=true` — nötig wegen eines npm-10.9-Arborist-Bugs bei diesem Dependency-Graphen.

```bash
cd frontend
npm install
npm run build      # erzeugt dist/
npm start          # ng serve
```

## Ablauf im Workshop (Vorschlag)

1. **Design-Gate** — die 3 offenen Entscheidungen aus [`06-definition-of-ready.md`](docs/refinement/06-definition-of-ready.md) gemeinsam klären (Persistenz, gleiche ISIN, Anlage-UX).
2. **Delivery live** — Acceptance-Tests aktivieren (rot) → `PortfolioService.totalValue()` und `absoluteProfitLoss()` gegen die Tests implementieren (US2/US3) → grün. Parallel FE-Anbindung.
3. **Technische Bewertung** — Clean Code / Architektur / Präzision (BigDecimal) gemeinsam beurteilen.
4. **Ausbaustufe live** — eine der beiden Erweiterungen aus dem PRD (transaktionsbasierte Erfassung *oder* Benchmark-Vergleich) durch das Setup laufen lassen.

## Ausbaustufen (aus dem PRD)
- **Ausbaustufe 1:** Transaktionsbasierte Erfassung (BUY/SELL → Bestand + Ø-Einstiegskurs).
- **Ausbaustufe 2:** Benchmark-Vergleich (prozentuale Rendite vs. Benchmark → Out-/Underperformance).

---
*Erstellt als Workshop-Vorbereitung. Das Refinement wurde entlang der Methodik der enthaltenen Skills erstellt und kann im Workshop mit den Orchestrator-Agenten live vertieft/neu erzeugt werden.*
