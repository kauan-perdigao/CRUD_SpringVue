export interface Categoria {
  id: number | null
  name: string
}

export interface Produto {
  id: number | null
  name: string
  price: number
  category: Categoria | null
}

export type CategoriaPayload = { name: string }
export type ProdutoPayload = { name: string; price: number; category: Categoria | null }
