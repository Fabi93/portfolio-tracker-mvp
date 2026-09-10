# Lessons Learned — aus echten Delivery-Läufen

_Konkrete, wiederverwendbare Erkenntnisse, damit die Pipeline dieselben Fehler nicht wiederholt.
Ergänzt DoD, Skills und Constitution. Vor jedem Delivery kurz überfliegen._

## A · Pipeline-Disziplin (Prozess)
- **Test-First vollständig — beide Ebenen sind Pflicht:** Backend-API **und** Frontend
  (Vitest-Component **+ Playwright-E2E**). In einem Lauf wurde der Frontend-QA-Schritt
  (`write-acceptance-tests` → Playwright) übersprungen und musste nachgezogen werden.
- **Ursache: die Pipeline ist Leitplanke, kein Enforcement.** Ohne erzwingende Instanz rutschen
  DoD-Punkte durch → **CI einrichten** (GitHub Actions: `mvn verify` + JaCoCo-Gate,
  `npm run test:unit` + Playwright, Trivy, optional Sonar). Das ist der fehlende „Hook".
- **Scans wirklich ausführen** und **echte Werte** dokumentieren — nie „grün" behaupten.
- Bei großen, potenziell breaking Änderungen (z. B. Platform-Upgrade): eigener Commit,
  danach **erneut scannen/testen** und Vorher/Nachher festhalten.

## B · Toolchain-Preflight (vor jedem Backend-Delivery prüfen)
- **JAVA_HOME setzen:** `export JAVA_HOME="$(/usr/libexec/java_home -v 21)"`.
  Sonst nimmt Maven ein falsches JDK → `release version 21 not supported` (z. B. JDK 15).
- **maven-compiler-plugin explizit pinnen** (z. B. 3.13.0, `<release>21</release>`). Der
  Maven-Default 3.1 kann Java 21 nicht („Quelloption 5").
- **`quarkus:dev` braucht Maven ≥ 3.8.6** (3.8.1 wird abgelehnt). Alternative zum Starten:
  `mvn package && java -jar target/quarkus-app/quarkus-run.jar`.
- **npm-Arborist-Bug** `Cannot read properties of null (reading 'edgesOut')` →
  `--legacy-peer-deps` bzw. `.npmrc: legacy-peer-deps=true` (trat bei Angular auf; Vue/Vite lief sauber).
- **`mvn -q` + Pipes:** `-q` versteckt die BUILD-/Test-Zusammenfassung; `... | tail` verfälscht
  den Exit-Code (PIPESTATUS). Ergebnis über **Surefire-Reports** oder `grep -E "BUILD (SUCCESS|FAILURE)"` prüfen.

## C · Tests
- **Test-Isolation bei `@QuarkusTest`:** `@ApplicationScoped`-Singletons teilen In-Memory-State
  über Testklassen → **`@BeforeEach`-Reset** (Service-`clear()`), sonst reihenfolgeabhängig/flaky
  und man kann nur Shape statt Werte prüfen (verletzt FIRST „Independent").
- **API-Tests echte Werte assert­en** (nicht nur Status/`notNullValue`).
- **Frontend:** Vitest (Component: Validierung/States/Emit) **+** Playwright (E2E): rollen-/
  label-basierte Selektoren, **eigene Testdaten je Lauf** (nicht auf geteilten Backend-State
  verlassen), `webServer` startet FE **und** BE bei Bedarf. Strict-Mode: Texte, die auch in
  Microcopy vorkommen, mit `{ exact: true }` treffen.

## D · Scans (Delivery/Review) — konkrete Kommandos & Stolpersteine
- **SonarQube:** `docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:community`.
  - Login ist **erzwungen** (admin/admin); Token per API: `curl -u admin:admin -X POST ".../api/user_tokens/generate?name=ci"`.
  - Analyse mit **voll-qualifiziertem Goal**: `org.sonarsource.scanner.maven:sonar-maven-plugin:sonar`
    (der `sonar:sonar`-Prefix ist Maven unbekannt → `NoPluginFoundForPrefix`).
  - **Coverage nur mit JaCoCo:** `jacoco-maven-plugin` (prepare-agent + report),
    `-Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml`.
  - Nach der Analyse serverseitige **CE-Verarbeitung abwarten** (`/api/ce/component` → SUCCESS),
    erst dann Metriken/Quality Gate abfragen.
  - Container kann **stoppen** (Ressourcen) → Dashboard leer. `docker start sonarqube` bringt ihn
    **mit Daten** zurück (H2 im Container). **`docker rm` löscht die Daten** endgültig.
- **Dependency-Scan (Trivy):** `trivy fs --scanners vuln backend` (auf das **Projekt mit `pom.xml`**,
  nicht nur die Jar-Dir — die fand „0 language-specific files"). Frontend:
  `trivy fs --scanners vuln --skip-dirs node_modules frontend` + `npm audit`.
- **Image-Scan** braucht ein **gebautes Image** (`quarkus-container-image-docker` →
  `-Dquarkus.container-image.build=true`, dann `trivy image <image>`). Ohne Image: „nicht ausgeführt: Grund".

## E · Security- & Design-Findings (generell mitnehmen)
- **Dependency-Freshness:** kein veraltetes Platform-Pinning. Gepinntes **Quarkus 3.15.1** erzeugte
  **63 CVEs** (1 CRIT/28 HIGH/…); Upgrade auf aktuelle Version (3.39.2) → **0 CVEs**, Tests blieben grün.
  → Vor Delivery aktuelle Version prüfen (Maven-Central-`maven-metadata.xml`) und **Dependency-Scan früh** fahren.
- **Thread-Safety:** In-Memory-Stores in Singletons ohne Synchronisation sind nicht thread-safe
  (MVP-tauglich; für echt: Synchronisation oder DB + Testcontainers).
- **Geldbeträge im Frontend als `number`** können Präzision verlieren (Backend liefert `BigDecimal`).
