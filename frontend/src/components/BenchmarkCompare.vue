<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { compareBenchmark } from '../api/portfolio'
import type { BenchmarkComparison } from '../api/types'

const props = withDefaults(defineProps<{ reloadKey?: number }>(), { reloadKey: 0 })

const benchmarks = [
  { id: 'MSCI_WORLD', label: 'MSCI World' },
  { id: 'SP500', label: 'S&P 500' },
  { id: 'MSCI_EM', label: 'MSCI Emerging Markets' },
]

const benchmarkId = ref('MSCI_WORLD')
const result = ref<BenchmarkComparison | null>(null)
const loading = ref(false)
const error = ref<string | null>(null)

const pct = new Intl.NumberFormat('de-DE', { minimumFractionDigits: 2, maximumFractionDigits: 2 })

function formatPct(value: number): string {
  return `${value > 0 ? '+' : ''}${pct.format(value)} %`
}

async function compare(): Promise<void> {
  loading.value = true
  error.value = null
  try {
    result.value = await compareBenchmark(benchmarkId.value)
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Unbekannter Fehler'
    result.value = null
  } finally {
    loading.value = false
  }
}

onMounted(compare)
watch(() => props.reloadKey, compare)
</script>

<template>
  <div class="card">
    <div class="benchmark-controls">
      <div class="field">
        <label for="bench-id">Benchmark</label>
        <select id="bench-id" v-model="benchmarkId" @change="compare">
          <option v-for="b in benchmarks" :key="b.id" :value="b.id">{{ b.label }}</option>
        </select>
      </div>
      <button type="button" @click="compare" :disabled="loading">
        {{ loading ? 'Vergleiche…' : 'Vergleichen' }}
      </button>
    </div>

    <p v-if="error" class="msg error" role="alert">{{ error }}</p>

    <div v-else-if="result" class="metrics">
      <div class="metric">
        <span class="metric-label">Portfolio-Rendite</span>
        <span class="metric-value">{{ formatPct(result.portfolioReturnPct) }}</span>
      </div>
      <div class="metric">
        <span class="metric-label">Benchmark-Rendite</span>
        <span class="metric-value">{{ formatPct(result.benchmarkReturnPct) }}</span>
      </div>
      <div class="metric">
        <span class="metric-label">Out-/Underperformance</span>
        <span class="metric-value" :class="result.outperformancePct < 0 ? 'loss' : 'gain'">
          {{ formatPct(result.outperformancePct) }}
        </span>
      </div>
    </div>
  </div>
</template>
