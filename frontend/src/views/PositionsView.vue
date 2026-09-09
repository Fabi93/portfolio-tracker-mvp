<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { listPositions } from '../api/portfolio'
import type { Position } from '../api/types'
import AddPositionForm from '../components/AddPositionForm.vue'
import PositionList from '../components/PositionList.vue'

const positions = ref<Position[]>([])
const loading = ref(true)
const error = ref<string | null>(null)

async function load(): Promise<void> {
  error.value = null
  try {
    positions.value = await listPositions()
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
    <h2 class="section-title">Positionen</h2>
    <p class="section-note">Bestandsbasierte Erfassung (US1–US3): eine Position je Eintrag mit fixem Kaufkurs.</p>
    <div class="grid">
      <AddPositionForm @added="load" />
      <PositionList :positions="positions" :loading="loading" :error="error" />
    </div>
  </section>
</template>
