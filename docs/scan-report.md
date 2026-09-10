# Scan-Report (Delivery/Review) — echte Werte

_Ergebnis der `run-scans`-Stufe. Alle Werte **gemessen**, nicht geschätzt. Stand: 2026-09-10._
_Genau 2 Review-Iterationen (Skill-Vorgabe); danach Offenes hier dokumentiert._

## SonarQube (Iteration 2, nach Fix)
Server: SonarQube 26.9 Community (Docker), Projekt `portfolio-tracker`.

| Metrik | Wert |
|---|---|
| **Quality Gate** | ✅ **PASS** |
| Bugs | 0 |
| Vulnerabilities | 0 |
| Security Hotspots | 0 |
| Code Smells | **0** (Iteration 1: 3 → in Iteration 1 gefixt) |
| Coverage | **86,1 %** (JaCoCo) |
| Duplication | 0,0 % |
| Ratings | Reliability **A** · Security **A** · Maintainability **A** |
| Lines of Code | 575 |

**Iteration-1-Findings (behoben):** 3× MAJOR Code Smell in `PositionTest` — `assertThrows`-Lambdas enthielten mehr als eine potenziell werfende Invocation (`new BigDecimal(...)`). Fix: Werte in Konstanten extrahiert → Lambda enthält nur noch den Konstruktor-Aufruf. Re-Scan: 0 Smells.

## Dependency-Scan — Backend (Trivy)
**63 CVEs** in den transitiven Abhängigkeiten:

| Schweregrad | Anzahl |
|---|---|
| CRITICAL | 1 |
| HIGH | 28 |
| MEDIUM | 31 |
| LOW | 3 |

Betroffene Pakete: Netty (`netty-codec-http/http2/handler/…`), Jackson (`jackson-core/databind`), Vert.x (`vertx-core/web`), Quarkus (`quarkus-rest`, `quarkus-vertx`, `quarkus-vertx-http`).
Beispiele: `quarkus-vertx-http` Authorization-Bypass (**HIGH**, CVE-2026-39852 / CVE-2026-50559), `quarkus-rest` Worker-Thread-Exhaustion (MEDIUM, CVE-2025-66560).

**Ursache:** gepinntes **Quarkus 3.15.1** (`pom.xml`) ist veraltet. **Empfohlener Fix:** `quarkus.platform.version` auf die aktuelle LTS anheben → räumt den Großteil der CVEs. **Nicht** in diesen 2 Iterationen umgesetzt (potenziell breaking, Entscheidung/Human-Gate).

## Dependency-Scan — Frontend
- **Trivy** (`package-lock.json`): **0** Vulnerabilities.
- **`npm audit`**: **0** Vulnerabilities.

## Image-/Container-Scan
**Nicht ausgeführt: Grund** — es wird **kein Container-Image** gebaut (Quarkus fast-jar, kein Dockerfile). Aktivierbar über die Extension `quarkus-container-image-docker` (`-Dquarkus.container-image.build=true`), dann `trivy image <image>`.

## Nach 2 Iterationen noch OFFEN (→ Entscheidung)
- **[1 CRITICAL / 28 HIGH] Backend-Dependency-CVEs** über veraltetes Quarkus 3.15.1 → Platform-Upgrade nötig (eigenes Ticket, da potenziell breaking).
- **[Info] Image-Scan** erst nach Bereitstellung eines Container-Images möglich.

## Reproduktion
```bash
# SonarQube starten
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:community
# Backend + Coverage + Analyse (Token aus SonarQube)
cd backend && export JAVA_HOME="$(/usr/libexec/java_home -v 21)"
mvn clean verify
mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.host.url=http://localhost:9000 -Dsonar.token=<TOKEN> \
  -Dsonar.projectKey=portfolio-tracker \
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
# Dependency-Scans
trivy fs --scanners vuln backend
trivy fs --scanners vuln --skip-dirs node_modules frontend
cd frontend && npm audit
```
