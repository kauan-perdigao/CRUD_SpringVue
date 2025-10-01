<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { Categoria, Produto } from '@/types'

const props = defineProps<{
  model?: { id: number | null; name: string; price: string; categoryId: number | null }
  categorias: Categoria[]
}>()

const formState = reactive<{ id: number | null; name: string; price: string; categoryId: number | null }>({
  id: null, name: '', price: '', categoryId: null,
})
const formValid = ref(false)

watch(() => props.model, (m) => {
  formState.id = m?.id ?? null
  formState.name = m?.name ?? ''
  formState.price = formatPrice(m?.price ?? '')
  formState.categoryId = m?.categoryId ?? null
}, { immediate: true })

function parsePrice(value: string): number {
  if (!value) return 0
  const normalizedValue = value.toString().replace(',', '.')
  return Number(normalizedValue)
}

function formatPrice(value: string | number): string {
  if (!value) return ''
  return value.toString().replace('.', ',')
}

function handlePriceInput(event: Event) {
  const target = event.target as HTMLInputElement
  let value = target.value
  
  value = value.replace(/[^0-9.,]/g, '')
  
  if (value.includes(',') && value.includes('.')) {
    value = value.replace(/\./g, '')
  }
  
  const parts = value.split(',')
  if (parts.length > 2) {
    value = parts[0] + ',' + parts.slice(1).join('')
  }
  
  formState.price = value
}

function validate(): boolean {
  return !!formState.name && parsePrice(formState.price) >= 0.01 && !!formState.categoryId
}

function data(): { id: number | null; name: string; price: string; categoryId: number | null } {
  const normalizedPrice = formState.price.replace(',', '.')
  return { ...formState, price: normalizedPrice }
}
defineExpose({ validate, data })
</script>

<template>
  <v-form v-model="formValid">
    <v-text-field
      label="Nome"
      v-model="formState.name"
      :rules="[v => !!v || 'Nome é obrigatório']"
      required
      clearable
    />
    <v-text-field
      label="Preço (R$)"
      v-model="formState.price"
      placeholder="Ex: 10,50 ou 0,01"
      :rules="[v => (parsePrice(v) >= 0.01) || 'Preço deve ser no mínimo R$ 0,01']"
      @input="handlePriceInput"
      required
      clearable
    />
    <v-select
      label="Categoria"
      v-model="formState.categoryId"
      :items="categorias"
      item-title="name"
      item-value="id"
      :rules="[v => !!v || 'Categoria é obrigatória']"
      required
    />
  </v-form>
</template>
