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
const loading = ref(false)
const error = ref<string | null>(null)
const reloadKey = ref(0)

async function reload(): Promise<void> {
  loading.value = true
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
    loading.value = false
  }
}

onMounted(reload)
</script>

<template>
  <main>
    <header>
      <h1>Portfolio Tracker</h1>
      <p class="subtitle">Manuelles Portfolio · Werte in EUR</p>
    </header>

    <PortfolioSummary :value="value" :pnl="pnl" :loading="loading" :error="error" />

    <BenchmarkCompare :reload-key="reloadKey" />

    <div class="grid">
      <AddPositionForm @added="reload" />
      <PositionList :positions="positions" :loading="loading" :error="error" />
    </div>

    <div class="grid">
      <AddTransactionForm @added="reload" />
      <HoldingList :holdings="holdings" :loading="loading" :error="error" />
    </div>
  </main>
</template>
