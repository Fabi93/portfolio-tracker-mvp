<script setup lang="ts">
import type { MoneyResponse } from '../api/types'

defineProps<{
  value: MoneyResponse | null
  loading: boolean
  error: string | null
}>()

const eur = new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' })
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
    </div>
  </section>
</template>
