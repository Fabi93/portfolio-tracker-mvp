<script setup lang="ts">
import { computed } from 'vue'
import type { MoneyResponse } from '../api/types'

const props = defineProps<{
  value: MoneyResponse | null
  pnl: MoneyResponse | null
  loading: boolean
  error: string | null
}>()

const eur = new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' })

const pnlText = computed(() => {
  if (!props.pnl) return '—'
  const formatted = eur.format(props.pnl.amount)
  return props.pnl.amount > 0 ? `+${formatted}` : formatted
})

const pnlClass = computed(() => {
  if (!props.pnl) return ''
  return props.pnl.amount < 0 ? 'loss' : 'gain'
})
</script>

<template>
  <section class="card summary" aria-labelledby="sum-title">
    <h2 id="sum-title">Übersicht</h2>

    <p v-if="loading" class="msg" role="status">Lädt…</p>
    <p v-else-if="error" class="msg error" role="alert">{{ error }}</p>

    <div v-else class="metrics">
      <div class="metric">
        <span class="metric-label">Gesamtwert</span>
        <span class="metric-value">{{ value ? eur.format(value.amount) : '—' }}</span>
      </div>
      <div class="metric">
        <span class="metric-label">Gewinn / Verlust</span>
        <span class="metric-value" :class="pnlClass">{{ pnlText }}</span>
      </div>
    </div>
  </section>
</template>
