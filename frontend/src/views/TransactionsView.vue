<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { listHoldings } from '../api/portfolio'
import type { Holding } from '../api/types'
import AddTransactionForm from '../components/AddTransactionForm.vue'
import HoldingList from '../components/HoldingList.vue'

const holdings = ref<Holding[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

async function load(): Promise<void> {
  error.value = null
  try {
    holdings.value = await listHoldings()
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Unbekannter Fehler'
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<template>
  <section class="section view">
    <h2 class="section-title">Transaktionen</h2>
    <p class="section-note">Transaktionsbasiert (Ausbaustufe 1): Bestand &amp; Ø-Einstiegskurs werden aus BUY/SELL abgeleitet.</p>
    <div class="grid">
      <AddTransactionForm @added="load" />
      <HoldingList :holdings="holdings" :loading="loading" :error="error" />
    </div>
  </section>
</template>
