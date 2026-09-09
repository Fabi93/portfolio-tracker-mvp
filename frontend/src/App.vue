<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getPerformance, getValue, listHoldings, listPositions } from './api/portfolio'
import type { Holding, MoneyResponse, Position } from './api/types'
import AddPositionForm from './components/AddPositionForm.vue'
import PositionList from './components/PositionList.vue'
import PortfolioSummary from './components/PortfolioSummary.vue'
import AddTransactionForm from './components/AddTransactionForm.vue'
import HoldingList from './components/HoldingList.vue'
import BenchmarkCompare from './components/BenchmarkCompare.vue'

const positions = ref<Position[]>([])
const holdings = ref<Holding[]>([])
const value = ref<MoneyResponse | null>(null)
const pnl = ref<MoneyResponse | null>(null)
const initialLoading = ref(true)
const error = ref<string | null>(null)
const reloadKey = ref(0)

async function reload(): Promise<void> {
  error.value = null
  try {
    const [loadedPositions, loadedValue, loadedPnl, loadedHoldings] = await Promise.all([
      listPositions(),
      getValue(),
      getPerformance(),
      listHoldings(),
    ])
    positions.value = loadedPositions
    value.value = loadedValue
    pnl.value = loadedPnl
    holdings.value = loadedHoldings
    reloadKey.value++
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Unbekannter Fehler'
  } finally {
    initialLoading.value = false
  }
}

onMounted(reload)
</script>

<template>
  <main>
    <header>
      <h1>Portfolio Tracker</h1>
      <p class="subtitle">Manuelles Portfolio · Performance auf einen Blick · Werte in EUR</p>
    </header>

    <PortfolioSummary :value="value" :pnl="pnl" :loading="initialLoading" :error="error" />

    <section class="section">
      <h2 class="section-title">Benchmark-Vergleich</h2>
      <p class="section-note">Prozentuale Rendite des Portfolios gegenüber einer Benchmark (Out-/Underperformance).</p>
      <BenchmarkCompare :reload-key="reloadKey" />
    </section>

    <section class="section">
      <h2 class="section-title">Positionen</h2>
      <p class="section-note">Bestandsbasierte Erfassung (US1–US3): eine Position je Eintrag mit fixem Kaufkurs.</p>
      <div class="grid">
        <AddPositionForm @added="reload" />
        <PositionList :positions="positions" :loading="initialLoading" :error="error" />
      </div>
    </section>

    <section class="section">
      <h2 class="section-title">Transaktionen</h2>
      <p class="section-note">Transaktionsbasiert (Ausbaustufe 1): Bestand &amp; Ø-Einstiegskurs werden aus BUY/SELL abgeleitet.</p>
      <div class="grid">
        <AddTransactionForm @added="reload" />
        <HoldingList :holdings="holdings" :loading="initialLoading" :error="error" />
      </div>
    </section>
  </main>
</template>
