<template>
  <v-card max-width="480" class="mx-auto">
    <v-card-title>Criar Conta</v-card-title>
    <v-card-text>
      <v-form v-model="ok">
        <v-text-field v-model="name" label="Nome" required />
        <v-text-field v-model="email" label="E-mail" type="email" required />
        <v-text-field v-model="password" label="Senha" type="password" required />
      </v-form>
      <v-alert v-if="err" type="error" class="mt-2">{{ err }}</v-alert>
    </v-card-text>
    <v-card-actions>
      <v-btn color="primary" :disabled="!ok" @click="doRegister">Criar</v-btn>
      <v-spacer />
      <RouterLink to="/login">Já tenho conta</RouterLink>
    </v-card-actions>
  </v-card>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { register } from '@/services/auth'
import { useRouter } from 'vue-router'

const router = useRouter()
const name = ref(''); const email = ref(''); const password = ref(''); const ok = ref(false); const err = ref<string|null>(null)

async function doRegister() {
  try {
    err.value = null
    await register(name.value, email.value, password.value)
    router.push('/produtos')
  } catch (e: any) {
    err.value = e?.response?.data || 'Falha no cadastro'
  }
}
</script>
