<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import ProdutoTable from '@/components/produtos/ProdutoTable.vue'
import ProdutoForm from '@/components/produtos/ProdutoForm.vue'
import type { Produto, Categoria, ProdutoPayload } from '@/types'
import { listarProdutos, criarProduto, atualizarProduto, excluirProduto } from '@/services/produtos'
import { listarCategorias } from '@/services/categorias'

const produtos = ref<Produto[]>([])
const categorias = ref<Categoria[]>([])
const carregando = ref(false)
const erro = ref<string | null>(null)

const dialog = ref(false)
const editando = ref(false)
const formModel = ref<{ id: number | null; name: string; price: number; categoryId: number | null }>({
  id: null, name: '', price: 0, categoryId: null,
})
const formRef = ref<InstanceType<typeof ProdutoForm> | null>(null)

async function carregar() {
  try {
    carregando.value = true
    erro.value = null
    const [ps, cs] = await Promise.all([listarProdutos(), listarCategorias()])
    produtos.value = ps
    categorias.value = cs
  } catch (e) {
    erro.value = 'Falha ao carregar produtos/categorias.'
    console.error(e)
  } finally {
    carregando.value = false
  }
}

function toPayload(): ProdutoPayload {
  const d = formRef.value!.data()
  const cat = categorias.value.find(c => c.id === d.categoryId) ?? null
  return {
    name: d.name,
    price: d.price,
    category: cat ? { id: cat.id, name: cat.name } : null,
  }
}

function abrirCriar() {
  editando.value = false
  formModel.value = { id: null, name: '', price: 0, categoryId: null }
  dialog.value = true
}
function abrirEditar(p: Produto) {
  editando.value = true
  formModel.value = { id: p.id, name: p.name, price: Number(p.price), categoryId: p.category?.id ?? null }
  dialog.value = true
}
function fechar() { dialog.value = false }

async function salvar() {
  try {
    const ok = formRef.value?.validate()
    if (!ok) return
    const payload = toPayload()
    const id = formModel.value.id
    if (editando.value && id !== null) {
      await atualizarProduto(id, payload)
    } else {
      await criarProduto(payload)
    }
    await carregar()
    fechar()
  } catch (e) {
    erro.value = 'Erro ao salvar o produto. Nome precisa ser único dentro da categoria.'
    console.error(e)
  }
}

async function confirmarExcluir(p: Produto) {
  if (!p.id) return
  if (confirm(`Excluir o produto "${p.name}"?`)) {
    try { await excluirProduto(p.id); await carregar() }
    catch (e) { erro.value = 'Não foi possível excluir o produto.'; console.error(e) }
  }
}

onMounted(carregar)
</script>

<template>
  <v-card>
    <v-card-title class="d-flex align-center justify-space-between">
      <span>Produtos</span>
      <v-btn color="primary" @click="abrirCriar"><v-icon icon="mdi-plus" /> Novo</v-btn>
    </v-card-title>

    <v-divider />

    <v-card-text>
      <v-alert v-if="erro" type="error" closable class="mb-4">{{ erro }}</v-alert>

      <ProdutoTable
        :items="produtos"
        :loading="carregando"
        @edit="abrirEditar"
        @delete="confirmarExcluir"
      />
    </v-card-text>
  </v-card>

  <v-dialog v-model="dialog" max-width="560">
    <v-card>
      <v-card-title>{{ editando ? 'Editar Produto' : 'Novo Produto' }}</v-card-title>
      <v-card-text>
        <ProdutoForm ref="formRef" :model="formModel" :categorias="categorias" />
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn variant="text" @click="fechar">Cancelar</v-btn>
        <v-btn color="primary" @click="salvar">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
