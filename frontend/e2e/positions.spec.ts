import { test, expect } from '@playwright/test'

test('Position hinzufügen erscheint im Bestand', async ({ page }) => {
  const isin = 'E2E' + Date.now()

  await page.goto('/positionen')
  await page.getByLabel('Wertpapier-Kennnummer (ISIN)').fill(isin)
  await page.getByLabel('Stückzahl').fill('5')
  await page.getByLabel('Kaufkurs (EUR)').fill('100')
  await page.getByRole('button', { name: 'Hinzufügen' }).click()

  await expect(page.getByRole('cell', { name: isin })).toBeVisible()
})

test('leere Eingabe zeigt einen Validierungsfehler', async ({ page }) => {
  await page.goto('/positionen')
  await page.getByRole('button', { name: 'Hinzufügen' }).click()

  await expect(page.getByRole('alert')).toBeVisible()
})
