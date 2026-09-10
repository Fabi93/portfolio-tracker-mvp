import { defineConfig, devices } from '@playwright/test'

// E2E-Tests fahren Frontend (Vite) und Backend (Quarkus) hoch, sofern nicht bereits laufend.
export default defineConfig({
  testDir: './e2e',
  timeout: 30_000,
  fullyParallel: false,
  workers: 1,
  reporter: 'list',
  use: {
    baseURL: 'http://localhost:5173',
    trace: 'on-first-retry',
  },
  projects: [{ name: 'chromium', use: { ...devices['Desktop Chrome'] } }],
  webServer: [
    {
      command: 'npm run dev',
      url: 'http://localhost:5173',
      reuseExistingServer: true,
      timeout: 60_000,
    },
    {
      command:
        'sh -c \'cd ../backend && export JAVA_HOME="$(/usr/libexec/java_home -v 21)" && (test -f target/quarkus-app/quarkus-run.jar || mvn -q package -DskipTests) && java -jar target/quarkus-app/quarkus-run.jar\'',
      url: 'http://localhost:8080/api/portfolio/positions',
      reuseExistingServer: true,
      timeout: 120_000,
    },
  ],
})
