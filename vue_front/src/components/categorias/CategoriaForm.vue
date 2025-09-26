<script lang="ts" setup>
import { reactive, watch, ref } from 'vue'
import type { Categoria } from '@/types'

const props = defineProps<{ model?: { id: number | null; name: string } }>()
const formState = reactive<{ id: number | null; name: string }>({ id: null, name: '' })
const formValid = ref(false)

watch(() => props.model, (m) => {
  formState.id = m?.id ?? null
  formState.name = m?.name ?? ''
}, { immediate: true })

function validate(): boolean {
  return !!formState.name
}
function data(): { id: number | null; name: string } {
  return { id: formState.id, name: formState.name }
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
  </v-form>
</template>
