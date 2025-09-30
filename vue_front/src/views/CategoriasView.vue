<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import CategoriaTable from '@/components/categorias/CategoriaTable.vue'
import CategoriaForm from '@/components/categorias/CategoriaForm.vue'
import type { Categoria } from '@/types'
import { listarCategorias, criarCategoria, atualizarCategoria, excluirCategoria } from '@/services/categorias'

const categorias = ref<Categoria[]>([])
const carregando = ref(false)
const erro = ref<string | null>(null)
const sucesso = ref<string | null>(null)

const dialog = ref(false)
const editando = ref(false)
const formModel = ref<{ id: number | null; name: string }>({ id: null, name: '' })
const formRef = ref<InstanceType<typeof CategoriaForm> | null>(null)

async function carregar() {
  try {
    carregando.value = true
    erro.value = null
    categorias.value = await listarCategorias()
  } catch (e) {
    erro.value = 'Falha ao carregar categorias.'
    console.error(e)
  } finally {
    carregando.value = false
  }
}

function abrirCriar() {
  editando.value = false
  formModel.value = { id: null, name: '' }
  dialog.value = true
}

function abrirEditar(item: Categoria) {
  editando.value = true
  formModel.value = { id: item.id, name: item.name }
  dialog.value = true
}

function fechar() {
  dialog.value = false
}

async function salvar() {
  try {
    const ok = formRef.value?.validate()
    if (!ok) return
    const data = formRef.value!.data()
    if (editando.value && data.id !== null) {
      await atualizarCategoria(data.id, { name: data.name })
      sucesso.value = 'Categoria atualizada com sucesso!'
    } else {
      await criarCategoria({ name: data.name })
      sucesso.value = 'Categoria criada com sucesso!'
    }
    erro.value = null // Limpa erros anteriores
    await carregar()
    fechar()
  } catch (e) {
    erro.value = 'Erro ao salvar a categoria. Verifique se o nome é único.'
    sucesso.value = null // Limpa sucessos anteriores
    console.error(e)
  }
}

async function confirmarExcluir(item: Categoria) {
  if (!item.id) return
  if (confirm(`Excluir a categoria "${item.name}"?`)) {
    try {
      await excluirCategoria(item.id)
      sucesso.value = `Categoria "${item.name}" excluída com sucesso!`
      erro.value = null
      await carregar()
    } catch (e) {
      erro.value = 'Não foi possível excluir. Há produtos usando esta categoria?'
      sucesso.value = null
      console.error(e)
    }
  }
}

onMounted(carregar)
</script>

<template>
  <v-card>
    <v-card-title class="d-flex align-center justify-space-between">
      <span>Categorias</span>
      <v-btn color="primary" @click="abrirCriar"><v-icon icon="mdi-plus" /> Nova</v-btn>
    </v-card-title>

    <v-divider />

    <v-card-text>
      <v-alert v-if="erro" type="error" closable class="mb-4" @click:close="erro = null">{{ erro }}</v-alert>
      <v-alert v-if="sucesso" type="success" closable class="mb-4" @click:close="sucesso = null">{{ sucesso }}</v-alert>

      <CategoriaTable
        :items="categorias"
        :loading="carregando"
        @edit="abrirEditar"
        @delete="confirmarExcluir"
      />
    </v-card-text>
  </v-card>

  <v-dialog v-model="dialog" max-width="480">
    <v-card>
      <v-card-title>{{ editando ? 'Editar Categoria' : 'Nova Categoria' }}</v-card-title>
      <v-card-text>
        <CategoriaForm ref="formRef" :model="formModel" />
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn variant="text" @click="fechar">Cancelar</v-btn>
        <v-btn color="primary" @click="salvar">Salvar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
