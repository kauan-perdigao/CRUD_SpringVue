import { createRouter, createWebHistory } from 'vue-router'
import { isAuthenticated } from '@/services/auth'

const routes = [
  { path: '/', redirect: '/login' }, 
  { path: '/login', component: () => import('@/views/LoginView.vue') },
  { path: '/register', component: () => import('@/views/RegisterView.vue') },
  { path: '/home', component: () => import('@/views/Home.vue'), meta: { requiresAuth: true } },
  { path: '/produtos', component: () => import('@/views/ProdutosView.vue'), meta: { requiresAuth: true } },
  { path: '/categorias', component: () => import('@/views/CategoriasView.vue'), meta: { requiresAuth: true } },
  { path: '/:pathMatch(.*)*', redirect: '/home' },
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to) => {
  if (to.meta?.requiresAuth && !isAuthenticated()) return '/login'

  if ((to.path === '/login' || to.path === '/register') && isAuthenticated()) return '/home'

  return true
})

export default router
