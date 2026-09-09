<script setup lang="ts">
import { ref } from 'vue'
import { addPosition } from '../api/portfolio'

const emit = defineEmits<{ added: [] }>()

const isin = ref('')
const quantity = ref<number | null>(null)
const buyInPrice = ref<number | null>(null)

const submitting = ref(false)
const error = ref<string | null>(null)
const success = ref(false)

function validationError(): string | null {
  if (!isin.value.trim()) return 'Bitte eine ISIN eingeben.'
  if (quantity.value === null || quantity.value <= 0) return 'Stückzahl muss größer als 0 sein.'
  if (buyInPrice.value === null || buyInPrice.value < 0) return 'Kaufkurs darf nicht negativ sein.'
  return null
}

async function onSubmit(): Promise<void> {
  error.value = null
  success.value = false
  const invalid = validationError()
  if (invalid) {
    error.value = invalid
    return
  }
  submitting.value = true
  try {
    await addPosition({ isin: isin.value.trim(), quantity: quantity.value!, buyInPrice: buyInPrice.value! })
    success.value = true
    isin.value = ''
    quantity.value = null
    buyInPrice.value = null
    emit('added')
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Unbekannter Fehler'
  } finally {
    submitting.value = false
  }
}
</script>

<template>
  <form class="card" @submit.prevent="onSubmit" aria-labelledby="add-title">
    <h2 id="add-title">Position hinzufügen</h2>

    <div class="field">
      <label for="isin">Wertpapier-Kennnummer (ISIN)</label>
      <input id="isin" v-model="isin" name="isin" type="text" autocomplete="off" placeholder="z. B. IE00B4L5Y983" />
    </div>

    <div class="field">
      <label for="quantity">Stückzahl</label>
      <input id="quantity" v-model.number="quantity" name="quantity" type="number" step="any" min="0" placeholder="z. B. 10" />
    </div>

    <div class="field">
      <label for="buyInPrice">Kaufkurs (EUR)</label>
      <input id="buyInPrice" v-model.number="buyInPrice" name="buyInPrice" type="number" step="any" min="0" placeholder="z. B. 90.00" />
    </div>

    <button type="submit" :disabled="submitting">
      {{ submitting ? 'Speichern…' : 'Hinzufügen' }}
    </button>

    <p v-if="error" class="msg error" role="alert">{{ error }}</p>
    <p v-else-if="success" class="msg success" role="status">Position hinzugefügt.</p>
  </form>
</template>
