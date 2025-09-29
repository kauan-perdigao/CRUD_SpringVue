<script lang="ts" setup>
import { computed, ref } from 'vue'
import type { Produto } from '@/types'

const props = defineProps<{
  items: Produto[]
  loading?: boolean
}>()

const emit = defineEmits<{
  (e: 'edit', item: Produto): void
  (e: 'delete', item: Produto): void
}>()

const search = ref('')
function norm(s: string) {
  return s.normalize('NFD').replace(/\p{Diacritic}/gu, '').toLowerCase()
}
const headers = [
  { title: 'ID', value: 'id', width: 80 },
  { title: 'Nome', value: 'name' },
  { title: 'Preço', value: 'price' },
  { title: 'Categoria', value: 'category' },
  { title: 'Ações', value: 'actions', sortable: false, align: 'end' as const, width: 120 },
]

const filtered = computed(() => {
  const q = norm(search.value.trim())
  if (!q) return props.items
  return props.items.filter(p => {
    const campos = [
      String(p.id ?? ''),
      p.name ?? '',
      String(p.price ?? ''),
      p.category?.name ?? '',
    ]
    return campos.some(c => norm(c).includes(q))
  })
})

function onClear() {
  search.value = ''
}
</script>

<template>
  <div>
    <v-text-field
      v-model="search"
      label="Pesquisar produtos"
      placeholder="Busque por ID, Nome, Preço ou Categoria…"
      prepend-inner-icon="mdi-magnify"
      clearable
      @click:clear="onClear"
      class="mb-4"
    />
    <v-data-table
      :headers="headers"
      :items="filtered"
      :loading="loading"
      item-key="id"
    >
      <template #item.category="{ item }">
        <span>{{ item.category?.name ?? '-' }}</span>
      </template>
      <template #item.price="{ item }">
        <span>R$ {{ Number(item.price).toFixed(2) }}</span>
      </template>
      <template #item.actions="{ item }">
        <v-btn size="small" icon="mdi-pencil" @click="emit('edit', item)"></v-btn>
        <v-btn size="small" color="error" icon="mdi-delete" @click="emit('delete', item)"></v-btn>
      </template>
    </v-data-table>
  </div>
</template>
