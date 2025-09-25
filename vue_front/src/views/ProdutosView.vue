<template>
  <v-card>
    <v-card-title class="d-flex align-center justify-space-between">
      <span>Produtos</span>
      <v-btn color="primary" @click="abrirCriar"><v-icon icon="mdi-plus" /> Novo</v-btn>
    </v-card-title>
    <v-divider />
    <v-card-text>
      <v-alert v-if="erro" type="error" closable class="mb-4">{{ erro }}</v-alert>
      <v-data-table
        :headers="headers"
        :items="produtos"
        :loading="carregando"
        item-key="id"
      >
        <template #item.category="{ item }">
          <span>{{ item.category?.name ?? '-' }}</span>
        </template>
        <template #item.price="{ item }">
          <span>R$ {{ Number(item.price).toFixed(2) }}</span>
        </template>
        <template #item.actions="{ item }">
          <v-btn size="small" icon="mdi-pencil" @click="abrirEditar(item)"></v-btn>
          <v-btn size="small" color="error" icon="mdi-delete" @click="confirmarExcluir(item)"></v-btn>
        </template>
      </v-data-table>
    </v-card-text>
  </v-card>

  <v-dialog v-model="dialog" max-width="560">
    <v-card>
      <v-card-title>{{ editando ? 'Editar Produto' : 'Novo Produto' }}</v-card-title>
      <v-card-text>
        <v-form v-model="formValido">
          <v-text-field
            label="Nome"
            v-model="form.name"
            :rules="[v => !!v || 'Nome é obrigatório']"
            required
            clearable
          />
          <v-text-field
            label="Preço"
            v-model.number="form.price"
            type="number"
            step="0.01"
            :rules="[v => v > 0 || 'Preço deve ser maior que 0']"
            required
          />
          <v-select
            label="Categoria"
            v-model="form.categoryId"
            :items="opcoesCategorias"
            item-title="name"
            item-value="id"
            :rules="[v => !!v || 'Categoria é obrigatória']"
            required
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
import { onMounted, ref, computed } from 'vue'
import type { Produto, Categoria, ProdutoPayload } from '@/types'
import { listarProdutos, criarProduto, atualizarProduto, excluirProduto } from '@/services/produtos'
import { listarCategorias } from '@/services/categorias'

const headers = [
  { title: 'ID', value: 'id', width: 80 },
  { title: 'Nome', value: 'name' },
  { title: 'Preço', value: 'price' },
  { title: 'Categoria', value: 'category' },
  { title: 'Ações', value: 'actions', sortable: false, align: 'end' as const, width: 120 },
]

const produtos = ref<Produto[]>([])
const categorias = ref<Categoria[]>([])
const carregando = ref(false)
const erro = ref<string | null>(null)

const dialog = ref(false)
const formValido = ref(false)
const editando = ref(false)
const form = ref<{ id: number | null; name: string; price: number; categoryId: number | null }>({
  id: null,
  name: '',
  price: 0,
  categoryId: null,
})

const opcoesCategorias = computed(() => categorias.value)

function toPayload(): ProdutoPayload {
  const categoria = categorias.value.find(c => c.id === form.value.categoryId) ?? null
  return {
    name: form.value.name,
    price: form.value.price,
    category: categoria ? { id: categoria.id, name: categoria.name } : null,
  }
}

function preencherForm(produto: Produto) {
  form.value = {
    id: produto.id,
    name: produto.name,
    price: Number(produto.price),
    categoryId: produto.category?.id ?? null,
  }
}

function limparForm() {
  form.value = { id: null, name: '', price: 0, categoryId: null }
  editando.value = false
}

async function carregar() {
  try {
    carregando.value = true
    erro.value = null
    const [ps, cs] = await Promise.all([listarProdutos(), listarCategorias()])
    produtos.value = ps
    categorias.value = cs
  } catch (e: unknown) {
    erro.value = 'Falha ao carregar produtos/categorias.'
    console.error(e)
  } finally {
    carregando.value = false
  }
}

function abrirCriar() {
  limparForm()
  dialog.value = true
}

function abrirEditar(item: Produto) {
  preencherForm(item)
  editando.value = true
  dialog.value = true
}

function fechar() {
  dialog.value = false
}

async function salvar() {
  try {
    if (!form.value.name || !form.value.categoryId || form.value.price <= 0) return
    const payload = toPayload()
    if (editando.value && form.value.id !== null) {
      await atualizarProduto(form.value.id, payload)
    } else {
      await criarProduto(payload)
    }
    await carregar()
    fechar()
  } catch (e: unknown) {
    erro.value = 'Erro ao salvar o produto. Nome precisa ser único dentro da categoria.'
    console.error(e)
  }
}

async function confirmarExcluir(item: Produto) {
  if (!item.id) return
  if (confirm(`Excluir o produto "${item.name}"?`)) {
    try {
      await excluirProduto(item.id)
      await carregar()
    } catch (e: unknown) {
      erro.value = 'Não foi possível excluir o produto.'
      console.error(e)
    }
  }
}

onMounted(carregar)
</script>