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

const confirmDialog = ref(false)
const categoriaParaExcluir = ref<Categoria | null>(null)

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
    erro.value = null 
    await carregar()
    fechar()
  } catch (e) {
    erro.value = 'Erro ao salvar a categoria. Verifique se o nome é único.'
    sucesso.value = null 
    console.error(e)
  }
}

function confirmarExcluir(item: Categoria) {
  categoriaParaExcluir.value = item
  confirmDialog.value = true
}

async function excluir() {
  const categoria = categoriaParaExcluir.value
  if (!categoria?.id) return
  
  try {
    await excluirCategoria(categoria.id)
    sucesso.value = `Categoria "${categoria.name}" foi excluída com sucesso!`
    erro.value = null
    await carregar()
  } catch (e) {
    erro.value = 'Não foi possível excluir. Há produtos usando esta categoria?'
    sucesso.value = null
    console.error(e)
  } finally {
    confirmDialog.value = false
    categoriaParaExcluir.value = null
  }
}

onMounted(carregar)
</script>

<template>
  <v-card>
    <v-card-title class="d-flex align-center justify-space-between">
      <span>Categorias</span>
      <v-btn color="primary" @click="abrirCriar"><v-icon icon="mdi-plus" /> Nova Categoria </v-btn>
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

  <!-- Diálogo de Confirmação de Exclusão -->
  <v-dialog v-model="confirmDialog" max-width="500" persistent>
    <v-card class="text-center pa-4">
      <v-card-text>
        <div class="mb-4">
          <v-avatar size="64" color="error" class="mb-3">
            <v-icon size="32" color="white">mdi-tag-remove-outline</v-icon>
          </v-avatar>
        </div>
        
        <h3 class="text-h6 mb-2">Confirmar Exclusão</h3>
        
        <p class="text-body-1 text-medium-emphasis mb-4">
          Tem certeza que deseja excluir a categoria
          <strong class="text-error">"{{ categoriaParaExcluir?.name }}"</strong>?
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

        <v-alert
          type="warning"
          variant="tonal"
          class="text-left mb-4"
          density="compact"
        >
          <template #prepend>
            <v-icon>mdi-alert-outline</v-icon>
          </template>
          Produtos vinculados a esta categoria ficarão sem categoria.
        </v-alert>

      </v-card-text>
      
      <v-card-actions class="justify-center pa-4">
        <v-btn
          variant="outlined"
          color="grey"
          size="large"
          @click="confirmDialog = false; categoriaParaExcluir = null"
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
          <v-icon start>mdi-tag-remove</v-icon>
          Excluir
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
