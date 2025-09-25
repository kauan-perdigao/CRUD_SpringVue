import { api } from './http'
import type { Categoria, CategoriaPayload } from '@/types'

const base = '/categorias'

export async function listarCategorias(): Promise<Categoria[]> {
  const { data } = await api.get<Categoria[]>(base)
  return data
}

export async function criarCategoria(payload: CategoriaPayload): Promise<Categoria> {
  const { data } = await api.post<Categoria>(base, payload)
  return data
}

export async function atualizarCategoria(id: number, payload: CategoriaPayload): Promise<Categoria> {
  const { data } = await api.put<Categoria>(`${base}/${id}`, payload)
  return data
}

export async function excluirCategoria(id: number): Promise<void> {
  await api.delete(`${base}/${id}`)
}