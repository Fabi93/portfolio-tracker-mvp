import { test, expect } from '@playwright/test'

test('Kauf-Transaktion erscheint im abgeleiteten Bestand', async ({ page }) => {
  const isin = 'TX' + Date.now()

  await page.goto('/transaktionen')
  await page.getByLabel('Art').selectOption('BUY')
  await page.getByLabel('ISIN').fill(isin)
  await page.getByLabel('Datum').fill('2026-01-15')
  await page.getByLabel('Menge').fill('4')
  await page.getByLabel('Kurs (EUR)').fill('50')
  await page.getByRole('button', { name: 'Transaktion buchen' }).click()

  await expect(page.getByRole('cell', { name: isin })).toBeVisible()
})
