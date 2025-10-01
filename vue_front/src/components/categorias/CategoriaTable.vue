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
const itemsPerPage = ref(10)
const currentPage = ref(1)
const itemsPerPageOptions = [
  { value: 5, title: '5' },
  { value: 10, title: '10' },
  { value: 25, title: '25' },
  { value: 50, title: '50' },
  { value: -1, title: 'Todos' }
]

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

const totalPages = computed(() => {
  if (itemsPerPage.value === -1) return 1
  return Math.ceil(filtered.value.length / itemsPerPage.value)
})

const paginatedItems = computed(() => {
  if (itemsPerPage.value === -1) return filtered.value
  const start = (currentPage.value - 1) * itemsPerPage.value
  const end = start + itemsPerPage.value
  return filtered.value.slice(start, end)
})

function getPageInfo(): string {
  if (filtered.value.length === 0) return '0 de 0'
  if (itemsPerPage.value === -1) return `1-${filtered.value.length} de ${filtered.value.length}`
  
  const start = (currentPage.value - 1) * itemsPerPage.value + 1
  const end = Math.min(currentPage.value * itemsPerPage.value, filtered.value.length)
  return `${start}-${end} de ${filtered.value.length}`
}

function onClear() {
  search.value = ''
  currentPage.value = 1
}
</script>

<template>
  <div>
    <v-text-field
      v-model="search"
      label="Pesquisar categorias"
      placeholder="Busque por ID ou Nome…"
      prepend-inner-icon="mdi-magnify"
      clearable
      @click:clear="onClear"
      class="mb-4"
    />

    <div class="mb-4">
      <div class="d-flex justify-space-between align-center mb-3">
        <div class="d-flex align-center">
          <span class="text-body-2 mr-4">Itens por página:</span>
          <v-select
            v-model="itemsPerPage"
            :items="itemsPerPageOptions"
            density="compact"
            variant="outlined"
            hide-details
            style="max-width: 100px;"
          ></v-select>
        </div>
        <div class="text-body-2">
          {{ getPageInfo() }}
        </div>
      </div>
      
      <div class="d-flex justify-center" v-if="totalPages > 1">
        <v-pagination
          v-model="currentPage"
          :length="totalPages"
          :total-visible="7"
          size="small"
          show-first-last-page
        ></v-pagination>
      </div>
    </div>

    <v-data-table
      :headers="headers"
      :items="paginatedItems"
      :loading="loading"
      item-key="id"
      hide-default-footer
      :no-data-text="'Nenhuma categoria encontrada'"
      :loading-text="'Carregando categorias...'"
    >
      <template #item.actions="{ item }">
        <v-btn size="small" icon="mdi-pencil" @click="emit('edit', item)"></v-btn>
        <v-btn size="small" color="error" icon="mdi-delete" @click="emit('delete', item)"></v-btn>
      </template>
    </v-data-table>

  </div>
</template>
