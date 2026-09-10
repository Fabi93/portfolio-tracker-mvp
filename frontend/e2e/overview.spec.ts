import { test, expect } from '@playwright/test'

test('Übersicht zeigt KPIs und den Benchmark-Vergleich', async ({ page }) => {
  await page.goto('/')

  await expect(page.getByRole('heading', { name: 'Übersicht' })).toBeVisible()
  await expect(page.getByText('Gesamtwert')).toBeVisible()
  await expect(page.getByText('Gewinn / Verlust')).toBeVisible()

  await page.getByLabel('Benchmark').selectOption('MSCI_WORLD')
  await page.getByRole('button', { name: 'Vergleichen' }).click()

  await expect(page.getByText('Out-/Underperformance', { exact: true })).toBeVisible()
})
