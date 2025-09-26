<script lang="ts" setup>
import { computed, ref } from 'vue'
import type { Categoria } from '@/types'

const props = defineProps<{
  items: Categoria[]
  loading?: boolean
}>()

const emit = defineEmits<{
  (e: 'edit', item: Categoria): void
  (e: 'delete', item: Categoria): void
}>()

const search = ref('')

function norm(s: string) {
  return s.normalize('NFD').replace(/\p{Diacritic}/gu, '').toLowerCase()
}

const headers = [
  { title: 'ID', value: 'id', width: 80 },
  { title: 'Nome', value: 'name' },
  { title: 'Ações', value: 'actions', sortable: false, align: 'end' as const, width: 120 },
]

const filtered = computed(() => {
  const q = norm(search.value.trim())
  if (!q) return props.items
  return props.items.filter(c =>
    norm(String(c.id ?? '')).includes(q) || norm(c.name ?? '').includes(q),
  )
})
</script>

<template>
  <div>
    <v-text-field
      v-model="search"
      label="Pesquisar categorias"
      placeholder="Busque por ID ou Nome…"
      prepend-inner-icon="mdi-magnify"
      clearable
      class="mb-4"
    />
    <v-data-table
      :headers="headers"
      :items="filtered"
      :loading="loading"
      item-key="id"
    >
      <template #item.actions="{ item }">
        <v-btn size="small" icon="mdi-pencil" @click="emit('edit', item)"></v-btn>
        <v-btn size="small" color="error" icon="mdi-delete" @click="emit('delete', item)"></v-btn>
      </template>
    </v-data-table>
  </div>
</template>
