<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import type { Categoria, Produto } from '@/types'

const props = defineProps<{
  model?: { id: number | null; name: string; price: number; categoryId: number | null }
  categorias: Categoria[]
}>()

const formState = reactive<{ id: number | null; name: string; price: number; categoryId: number | null }>({
  id: null, name: '', price: 0, categoryId: null,
})
const formValid = ref(false)

watch(() => props.model, (m) => {
  formState.id = m?.id ?? null
  formState.name = m?.name ?? ''
  formState.price = Number(m?.price ?? 0)
  formState.categoryId = m?.categoryId ?? null
}, { immediate: true })

function validate(): boolean {
  return !!formState.name && formState.price > 0 && !!formState.categoryId
}
function data(): { id: number | null; name: string; price: number; categoryId: number | null } {
  return { ...formState }
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
      label="Preço"
      v-model.number="formState.price"
      type="number"
      step="0.01"
      :rules="[v => v > 0 || 'Preço deve ser maior que 0']"
      required
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
