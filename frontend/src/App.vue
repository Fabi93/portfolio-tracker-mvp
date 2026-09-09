<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { listPositions } from './api/portfolio'
import type { Position } from './api/types'
import AddPositionForm from './components/AddPositionForm.vue'
import PositionList from './components/PositionList.vue'

const positions = ref<Position[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

async function reload(): Promise<void> {
  loading.value = true
  error.value = null
  try {
    positions.value = await listPositions()
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

    <div class="grid">
      <AddPositionForm @added="reload" />
      <PositionList :positions="positions" :loading="loading" :error="error" />
    </div>
  </main>
</template>
