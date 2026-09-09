<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getPerformance, getValue } from '../api/portfolio'
import type { MoneyResponse } from '../api/types'
import PortfolioSummary from '../components/PortfolioSummary.vue'
import BenchmarkCompare from '../components/BenchmarkCompare.vue'

const value = ref<MoneyResponse | null>(null)
const pnl = ref<MoneyResponse | null>(null)
const loading = ref(true)
const error = ref<string | null>(null)

async function load(): Promise<void> {
  loading.value = true
  error.value = null
  try {
    const [loadedValue, loadedPnl] = await Promise.all([getValue(), getPerformance()])
    value.value = loadedValue
    pnl.value = loadedPnl
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Unbekannter Fehler'
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<template>
  <div class="view">
    <PortfolioSummary :value="value" :pnl="pnl" :loading="loading" :error="error" />

    <section class="section">
      <h2 class="section-title">Benchmark-Vergleich</h2>
      <p class="section-note">Prozentuale Rendite des Portfolios gegenüber einer Benchmark (Out-/Underperformance).</p>
      <BenchmarkCompare />
    </section>
  </div>
</template>
