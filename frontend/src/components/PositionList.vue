<script setup lang="ts">
import type { Position } from '../api/types'

defineProps<{
  positions: Position[]
  loading: boolean
  error: string | null
}>()

const eur = new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' })
</script>

<template>
  <section class="card" aria-labelledby="list-title">
    <h2 id="list-title">Positionen</h2>

    <p v-if="loading" class="msg" role="status">Lädt…</p>
    <p v-else-if="error" class="msg error" role="alert">{{ error }}</p>
    <p v-else-if="positions.length === 0" class="msg">Noch keine Position — jetzt hinzufügen.</p>

    <table v-else>
      <thead>
        <tr>
          <th scope="col">ISIN</th>
          <th scope="col" class="num">Stückzahl</th>
          <th scope="col" class="num">Kaufkurs</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(p, i) in positions" :key="`${p.isin}-${i}`">
          <td>{{ p.isin }}</td>
          <td class="num">{{ p.quantity }}</td>
          <td class="num">{{ eur.format(p.buyInPrice) }}</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>
