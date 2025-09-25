import { createRouter, createWebHistory } from 'vue-router'
import { isAuthenticated } from '@/services/auth'

const routes = [
  { path: '/', component: () => import('@/views/Home.vue') },
  { path: '/login', component: () => import('@/views/LoginView.vue') },
  { path: '/register', component: () => import('@/views/RegisterView.vue') },
  { path: '/produtos', component: () => import('@/views/ProdutosView.vue'), meta: { requiresAuth: true } },
  { path: '/categorias', component: () => import('@/views/CategoriasView.vue'), meta: { requiresAuth: true } },
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to) => {
  if (to.meta?.requiresAuth && !isAuthenticated()) return '/login'
  return true
})

export default router
