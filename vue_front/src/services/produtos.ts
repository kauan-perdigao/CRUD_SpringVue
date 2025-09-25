import { api } from './http'
import type { Produto, ProdutoPayload } from '@/types'

const base = '/produtos'

export async function listarProdutos(): Promise<Produto[]> {
  const { data } = await api.get<Produto[]>(base)
  return data
}

export async function criarProduto(payload: ProdutoPayload): Promise<Produto> {
  const { data } = await api.post<Produto>(base, payload)
  return data
}

export async function atualizarProduto(id: number, payload: ProdutoPayload): Promise<Produto> {
  const { data } = await api.put<Produto>(`${base}/${id}`, payload)
  return data
}

export async function excluirProduto(id: number): Promise<void> {
  await api.delete(`${base}/${id}`)
}