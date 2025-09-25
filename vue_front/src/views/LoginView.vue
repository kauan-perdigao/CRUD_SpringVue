<template>
  <v-card max-width="420" class="mx-auto">
    <v-card-title>Entrar</v-card-title>
    <v-card-text>
      <v-form v-model="ok">
        <v-text-field v-model="email" label="E-mail" type="email" required />
        <v-text-field v-model="password" label="Senha" type="password" required />
      </v-form>
      <v-alert v-if="err" type="error" class="mt-2">{{ err }}</v-alert>
    </v-card-text>
    <v-card-actions>
      <v-btn color="primary" :disabled="!ok" @click="doLogin">Entrar</v-btn>
      <v-spacer />
      <RouterLink to="/register">Criar conta</RouterLink>
    </v-card-actions>
  </v-card>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { login } from '@/services/auth'
import { useRouter } from 'vue-router'

const router = useRouter()
const email = ref(''); const password = ref(''); const ok = ref(false); const err = ref<string|null>(null)

async function doLogin() {
  try {
    err.value = null
    await login(email.value, password.value)
    router.push('/produtos')
  } catch (e: any) {
    err.value = e?.response?.data || 'Falha no login'
  }
}
</script>
