<script setup lang="ts">
import { ref } from 'vue'
import { addTransaction } from '../api/portfolio'
import type { TransactionType } from '../api/types'

const emit = defineEmits<{ added: [] }>()

const type = ref<TransactionType>('BUY')
const isin = ref('')
const date = ref(new Date().toISOString().slice(0, 10))
const quantity = ref<number | null>(null)
const price = ref<number | null>(null)

const submitting = ref(false)
const error = ref<string | null>(null)
const success = ref(false)

function validationError(): string | null {
  if (!isin.value.trim()) return 'Bitte eine ISIN eingeben.'
  if (!date.value) return 'Bitte ein Datum wählen.'
  if (quantity.value === null || quantity.value <= 0) return 'Menge muss größer als 0 sein.'
  if (price.value === null || price.value < 0) return 'Kurs darf nicht negativ sein.'
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
    await addTransaction({
      type: type.value,
      isin: isin.value.trim(),
      date: date.value,
      quantity: quantity.value!,
      price: price.value!,
    })
    success.value = true
    isin.value = ''
    quantity.value = null
    price.value = null
    emit('added')
  } catch (e) {
    error.value = e instanceof Error ? e.message : 'Unbekannter Fehler'
  } finally {
    submitting.value = false
  }
}
</script>

<template>
  <form class="card" @submit.prevent="onSubmit" aria-labelledby="tx-title">
    <h2 id="tx-title">Transaktion erfassen</h2>

    <div class="field">
      <label for="tx-type">Art</label>
      <select id="tx-type" v-model="type" name="type">
        <option value="BUY">Kauf (BUY)</option>
        <option value="SELL">Verkauf (SELL)</option>
      </select>
    </div>

    <div class="field">
      <label for="tx-isin">ISIN</label>
      <input id="tx-isin" v-model="isin" name="isin" type="text" autocomplete="off" placeholder="z. B. IE00B4L5Y983" />
    </div>

    <div class="field">
      <label for="tx-date">Datum</label>
      <input id="tx-date" v-model="date" name="date" type="date" />
    </div>

    <div class="field">
      <label for="tx-quantity">Menge</label>
      <input id="tx-quantity" v-model.number="quantity" name="quantity" type="number" step="any" min="0" placeholder="z. B. 10" />
    </div>

    <div class="field">
      <label for="tx-price">Kurs (EUR)</label>
      <input id="tx-price" v-model.number="price" name="price" type="number" step="any" min="0" placeholder="z. B. 90.00" />
    </div>

    <button type="submit" :disabled="submitting">
      {{ submitting ? 'Speichern…' : 'Transaktion buchen' }}
    </button>

    <p v-if="error" class="msg error" role="alert">{{ error }}</p>
    <p v-else-if="success" class="msg success" role="status">Transaktion gebucht.</p>
  </form>
</template>
