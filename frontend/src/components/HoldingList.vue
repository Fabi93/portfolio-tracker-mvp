<script setup lang="ts">
import type { Holding } from '../api/types'

defineProps<{
  holdings: Holding[]
  loading: boolean
  error: string | null
}>()

const eur = new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' })
</script>

<template>
  <section class="card" aria-labelledby="holdings-title">
    <h2 id="holdings-title">Bestände (aus Transaktionen)</h2>

    <p v-if="loading" class="msg" role="status">Lädt…</p>
    <p v-else-if="error" class="msg error" role="alert">{{ error }}</p>
    <p v-else-if="holdings.length === 0" class="msg">Noch keine Transaktionen erfasst.</p>

    <table v-else>
      <thead>
        <tr>
          <th scope="col">ISIN</th>
          <th scope="col" class="num">Menge</th>
          <th scope="col" class="num">Ø-Einstieg</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="h in holdings" :key="h.isin">
          <td>{{ h.isin }}</td>
          <td class="num">{{ h.quantity }}</td>
          <td class="num">{{ eur.format(h.averageBuyIn) }}</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>
