import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/home' }, 
  { path: '/home', component: () => import('@/views/Home.vue') },
  { path: '/produtos', component: () => import('@/views/ProdutosView.vue') },
  { path: '/categorias', component: () => import('@/views/CategoriasView.vue') },
  { path: '/:pathMatch(.*)*', redirect: '/home' },
]

const router = createRouter({ history: createWebHistory(), routes })

export default router
