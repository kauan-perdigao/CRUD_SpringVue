<template>
  <v-card>
    <v-card-title class="d-flex align-center justify-space-between">
      <span>Categorias</span>
      <v-btn color="primary" @click="abrirCriar"><v-icon icon="mdi-plus" /> Nova</v-btn>
    </v-card-title>
    <v-divider />
    <v-card-text>
      <v-alert v-if="erro" type="error" closable class="mb-4">{{ erro }}</v-alert>
      <v-data-table
        :headers="headers"
        :items="categorias"
        :loading="carregando"
        item-key="id"
      >
        <template #item.actions="{ item }">
          <v-btn size="small" icon="mdi-pencil" @click="abrirEditar(item)"></v-btn>
          <v-btn size="small" color="error" icon="mdi-delete" @click="confirmarExcluir(item)"></v-btn>
        </template>
      </v-data-table>
    </v-card-text>
  </v-card>

  <v-dialog v-model="dialog" max-width="480">
    <v-card>
      <v-card-title>{{ editando ? 'Editar Categoria' : 'Nova Categoria' }}</v-card-title>
      <v-card-text>
        <v-form v-model="formValido">
          <v-text-field
            label="Nome"
            v-model="form.name"
            :rules="[v => !!v || 'Nome é obrigatório']"
            required
            clearable
          />
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn variant="text" @click="fechar">Cancelar</v-btn>
        <v-btn color="primary" :disabled="!formValido" @click="salvar">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import type { Categoria } from '@/types'
import { listarCategorias, criarCategoria, atualizarCategoria, excluirCategoria } from '@/services/categorias'

const headers = [
  { title: 'ID', value: 'id', width: 80 },
  { title: 'Nome', value: 'name' },
  { title: 'Ações', value: 'actions', sortable: false, align: 'end' as const, width: 120 },
]

const categorias = ref<Categoria[]>([])
const carregando = ref(false)
const erro = ref<string | null>(null)

const dialog = ref(false)
const formValido = ref(false)
const editando = ref(false)
const form = ref<{ id: number | null; name: string }>({ id: null, name: '' })

function limparForm() {
  form.value = { id: null, name: '' }
  editando.value = false
}

async function carregar() {
  try {
    carregando.value = true
    erro.value = null
    categorias.value = await listarCategorias()
  } catch (e: unknown) {
    erro.value = 'Falha ao carregar categorias.'
    console.error(e)
  } finally {
    carregando.value = false
  }
}

function abrirCriar() {
  limparForm()
  dialog.value = true
}

function abrirEditar(item: Categoria) {
  form.value = { id: item.id, name: item.name }
  editando.value = true
  dialog.value = true
}

function fechar() {
  dialog.value = false
}

async function salvar() {
  try {
    if (!form.value.name) return
    if (editando.value && form.value.id !== null) {
      await atualizarCategoria(form.value.id, { name: form.value.name })
    } else {
      await criarCategoria({ name: form.value.name })
    }
    await carregar()
    fechar()
  } catch (e: unknown) {
    erro.value = 'Erro ao salvar a categoria. Verifique se o nome é único.'
    console.error(e)
  }
}

async function confirmarExcluir(item: Categoria) {
  if (!item.id) return
  if (confirm(`Excluir a categoria "${item.name}"?`)) {
    try {
      await excluirCategoria(item.id)
      await carregar()
    } catch (e: unknown) {
      erro.value = 'Não foi possível excluir. Há produtos usando esta categoria?'
      console.error(e)
    }
  }
}

onMounted(carregar)
</script>