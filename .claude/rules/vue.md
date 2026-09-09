---
paths:
  - "**/*.vue"
---

# Vue 3 Best Practices — allgemeine Regeln

Framework-spezifische Ergänzung zu `clean-code.md` + `typescript.md`. Orientiert an modernem
Vue 3 (Composition API, `<script setup>`, TypeScript). Nicht projektspezifisch.

## Komponenten
- **`<script setup lang="ts">`** + **Composition API** für neuen Code (keine Options API).
- Eine SFC = **eine Verantwortung**; klein halten. Präsentations- von Container-Komponenten trennen.
- **Typisierte** `defineProps<...>()` / `defineEmits<...>()`; keine untypisierten Props.
- **Props runter, Events hoch** — **Props nie mutieren** (stattdessen `emit` und der Parent ändert).

## Reaktivität
- `ref` für Primitives, `reactive` für Objekte; **abgeleitete Werte über `computed`**, nicht im Template berechnen.
- **`reactive` nicht destrukturieren** (verliert Reaktivität) — `toRefs`/`storeToRefs` nutzen.
- `watch`/`watchEffect` sparsam und mit Cleanup; kein Zustands-„Zauber" per Seiteneffekt.

## Templates
- `v-for` **immer mit stabilem `:key`**; **nicht** `v-if` und `v-for` am selben Element.
- Keine komplexe Logik im Template → in `computed`/Methoden auslagern.
- Zahlen/Währung über `Intl` formatieren (Locale-fähig).

## Struktur & Wiederverwendung
- Wiederverwendbare Logik in **Composables** (`useXxx`) statt Mixins.
- **Scoped Styles** (`<style scoped>`); globale Styles nur bewusst.
- Geteilten Zustand über **Pinia** (nicht globale mutable Objekte); lokalen Zustand lokal halten.

## Daten & Zustände
- HTTP-Aufrufe in Services/Composables kapseln (nicht direkt in der Komponente verstreut); **typisierte** Responses.
- **Alle Zustände** behandeln: **empty / loading / error / success** — nicht nur den Happy-Flow; Nutzer-Feedback nach Aktionen.

## Barrierefreiheit & Don'ts
- Semantisches HTML, Labels/`aria-*`, sichtbarer Fokus, Tastaturbedienung.
- **Keine direkte DOM-Manipulation** — Template-Refs/Bindings statt `document.*`.
- Kein `any`; keine Logik im Modul-Top-Level, die Seiteneffekte beim Import auslöst.
