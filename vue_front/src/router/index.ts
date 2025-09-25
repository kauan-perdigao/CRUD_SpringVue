import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  { path: '/', component: () => import('@/views/Home.vue'), meta: { title: 'Início', icon: 'mdi-home-outline' } },
  { path: '/produtos', component: () => import('@/views/ProdutosView.vue'), meta: { title: 'Produtos', icon: 'mdi-package-variant' } },
  { path: '/categorias', component: () => import('@/views/CategoriasView.vue'), meta: { title: 'Categorias', icon: 'mdi-shape' } },
]

export default createRouter({ history: createWebHistory(), routes })
