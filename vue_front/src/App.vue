<script lang="ts" setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'

const route = useRoute()

const menuEntries = computed(() => {
  if (route.path.startsWith('/produtos')) {
    return [
      { title: 'Início', icon: 'mdi-home', to: '/home' },
      { title: 'Categorias', icon: 'mdi-shape', to: '/categorias' },
    ]
  }
  if (route.path.startsWith('/categorias')) {
    return [
      { title: 'Início', icon: 'mdi-home', to: '/home' },
      { title: 'Produtos', icon: 'mdi-package-variant', to: '/produtos' },
    ]
  }
  return []
})

const showMenu = computed(() => menuEntries.value.length > 0)
</script>

<template>
  <v-app>
    <v-app-bar color="primary" density="comfortable" app>
      <v-menu v-if="showMenu" location="bottom start">
        <template #activator="{ props }">
          <v-btn v-bind="props" icon variant="text" class="mr-2">
            <v-icon>mdi-menu</v-icon>
          </v-btn>
        </template>

        <v-list density="comfortable" min-width="220">
          <v-list-item
            v-for="item in menuEntries"
            :key="item.to"
            :to="item.to"
            link
            :prepend-icon="item.icon"
            :title="item.title"
          />
        </v-list>
      </v-menu>

      <v-app-bar-title>Gerenciador de Produtos Domésticos</v-app-bar-title>

      <v-spacer />
    </v-app-bar>

    <v-main>
      <v-container class="py-6">
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>
