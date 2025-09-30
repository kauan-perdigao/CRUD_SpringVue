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

const snackbar = ref(false)
const snackbarMessage = ref('')
const snackbarColor = ref('success')

const dialog = ref(false)
const editando = ref(false)
const formModel = ref<{ id: number | null; name: string; price: number; categoryId: number | null }>({
  id: null, name: '', price: 0, categoryId: null,
})
const formRef = ref<InstanceType<typeof ProdutoForm> | null>(null)
const confirmDialog = ref(false)
const produtoParaExcluir = ref<Produto | null>(null)

function mostrarMensagem(mensagem: string, cor: 'success' | 'error' = 'success') {
  snackbarMessage.value = mensagem
  snackbarColor.value = cor
  snackbar.value = true
}

async function carregar() {
  try {
    carregando.value = true
    const [ps, cs] = await Promise.all([listarProdutos(), listarCategorias()])
    produtos.value = ps
    categorias.value = cs
  } catch (e) {
    mostrarMensagem('Falha ao carregar produtos.', 'error')
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
      mostrarMensagem('Produto atualizado com sucesso!')
    } else {
      await criarProduto(payload)
      mostrarMensagem('Produto criado com sucesso!')
    }
    await carregar()
    fechar()
  } catch (e) {
    mostrarMensagem('Erro ao salvar o produto. Nome precisa ser único dentro da categoria.', 'error')
    console.error(e)
  }
}

function confirmarExcluir(p: Produto) {
  produtoParaExcluir.value = p
  confirmDialog.value = true
}

async function excluir() {
  const produto = produtoParaExcluir.value
  if (!produto?.id) return
  
  try { 
    await excluirProduto(produto.id)
    mostrarMensagem(`Produto "${produto.name}" foi excluído com sucesso!`)
    await carregar()
  }
  catch (e) { 
    mostrarMensagem('Não foi possível excluir o produto.', 'error')
    console.error(e) 
  } finally {
    confirmDialog.value = false
    produtoParaExcluir.value = null
  }
}

onMounted(carregar)
</script>

<template>
  <v-card>
    <v-card-title class="d-flex align-center justify-space-between">
      <span>Produtos</span>
      <v-btn color="primary" @click="abrirCriar"><v-icon icon="mdi-plus" /> Novo Produto </v-btn>
    </v-card-title>

    <v-divider />

    <v-card-text>


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

  <v-dialog v-model="confirmDialog" max-width="500" persistent>
    <v-card class="text-center pa-4">
      <v-card-text>
        <div class="mb-4">
          <v-avatar size="64" color="error" class="mb-3">
            <v-icon size="32" color="white">mdi-delete-outline</v-icon>
          </v-avatar>
        </div>
        
        <h3 class="text-h6 mb-2">Confirmar Exclusão</h3>
        
        <p class="text-body-1 text-medium-emphasis mb-4">
          Tem certeza que deseja excluir o produto
          <strong class="text-error">"{{ produtoParaExcluir?.name }}"</strong>?
        </p>
        
        <v-alert
          type="warning"
          variant="tonal"
          class="text-left mb-4"
          density="compact"
        >
          <template #prepend>
            <v-icon>mdi-alert-outline</v-icon>
          </template>
          Esta ação não poderá ser desfeita.
        </v-alert>
      </v-card-text>
      
      <v-card-actions class="justify-center pa-4">
        <v-btn
          variant="outlined"
          color="grey"
          size="large"
          @click="confirmDialog = false; produtoParaExcluir = null"
          class="px-6"
        >
          <v-icon start>mdi-close</v-icon>
          Cancelar
        </v-btn>
        
        <v-btn
          color="error"
          size="large"
          @click="excluir"
          class="px-6 ml-3"
        >
          <v-icon start>mdi-delete</v-icon>
          Excluir
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-snackbar
    v-model="snackbar"
    :color="snackbarColor"
    timeout="4000"
    location="bottom center"
    class="font-weight-medium"
  >
    <v-icon start size="20">
      {{ snackbarColor === 'success' ? 'mdi-check-circle' : 'mdi-alert-circle' }}
    </v-icon>
    {{ snackbarMessage }}
    
    <template v-slot:actions>
      <v-btn
        icon="mdi-close"
        size="small"
        @click="snackbar = false"
      ></v-btn>
    </template>
  </v-snackbar>
</template>
