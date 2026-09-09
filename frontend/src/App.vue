<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header class="app-header">
    <div class="brand">
      <h1>Portfolio Tracker</h1>
      <p class="subtitle">Performance auf einen Blick · EUR</p>
    </div>
    <nav class="nav" aria-label="Hauptnavigation">
      <RouterLink to="/">Übersicht</RouterLink>
      <RouterLink to="/positionen">Positionen</RouterLink>
      <RouterLink to="/transaktionen">Transaktionen</RouterLink>
    </nav>
  </header>

  <main>
    <RouterView />
  </main>
</template>
