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

| | CRITICAL | HIGH | MEDIUM | LOW | Total |
|---|---|---|---|---|---|
| **Vorher (Quarkus 3.15.1)** | 1 | 28 | 31 | 3 | **63** |
| **Nachher (Quarkus 3.39.2)** | 0 | 0 | 0 | 0 | **0** ✅ |

**Ursache & Fix:** Das gepinnte **Quarkus 3.15.1** war veraltet (CVEs in Netty/Jackson/Vert.x/quarkus-vertx-http, u. a. Authorization-Bypass CVE-2026-39852/50559). **Behoben** durch Upgrade `quarkus.platform.version` → **3.39.2**; danach **0 CVEs** und **39/39 Backend-Tests weiterhin grün**.

## Dependency-Scan — Frontend
- **Trivy** (`package-lock.json`): **0** Vulnerabilities.
- **`npm audit`**: **0** Vulnerabilities.

## Image-/Container-Scan
**Nicht ausgeführt: Grund** — es wird **kein Container-Image** gebaut (Quarkus fast-jar, kein Dockerfile). Aktivierbar über die Extension `quarkus-container-image-docker` (`-Dquarkus.container-image.build=true`), dann `trivy image <image>`.

## Nach 2 Iterationen noch OFFEN
- **[Info] Image-Scan** erst nach Bereitstellung eines Container-Images möglich.
- _(Backend-Dependency-CVEs: **behoben** durch Quarkus-Upgrade 3.15.1 → 3.39.2, siehe oben.)_

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
