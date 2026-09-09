<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getPerformance, getValue, listPositions } from './api/portfolio'
import type { MoneyResponse, Position } from './api/types'
import AddPositionForm from './components/AddPositionForm.vue'
import PositionList from './components/PositionList.vue'
import PortfolioSummary from './components/PortfolioSummary.vue'

const positions = ref<Position[]>([])
const value = ref<MoneyResponse | null>(null)
const pnl = ref<MoneyResponse | null>(null)
const loading = ref(false)
const error = ref<string | null>(null)

async function reload(): Promise<void> {
  loading.value = true
  error.value = null
  try {
    const [loadedPositions, loadedValue, loadedPnl] = await Promise.all([
      listPositions(),
      getValue(),
      getPerformance(),
    ])
    positions.value = loadedPositions
    value.value = loadedValue
    pnl.value = loadedPnl
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

    <div class="grid">
      <AddPositionForm @added="reload" />
      <PositionList :positions="positions" :loading="loading" :error="error" />
    </div>
  </main>
</template>
