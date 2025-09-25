import { api } from './http'
import type { AuthToken } from '@/types/auth'

export async function login(email: string, password: string): Promise<AuthToken> {
  const { data } = await api.post<AuthToken>('/auth/login', { email, password })
  localStorage.setItem('auth', JSON.stringify(data))
  return data
}

export async function register(name: string, email: string, password: string): Promise<AuthToken> {
  const { data } = await api.post<AuthToken>('/auth/register', { name, email, password })
  localStorage.setItem('auth', JSON.stringify(data))
  return data
}

export function logout() {
  localStorage.removeItem('auth')
}

export function isAuthenticated(): boolean {
  return !!localStorage.getItem('auth')
}
