# Gerenciador de Alimentos (Vue 3 + Vuetify + TS)

Frontend simples para CRUD de **Produtos** e **Categorias**, integrado ao backend Spring Boot.

## Requisitos
- Node.js 18+
- Backend rodando em `http://localhost:8080` (já com CORS para `http://localhost:5173`)

## Como rodar
```bash
# 1) Entre na pasta
cd frontend-vue-crud

# 2) Instale
npm install

# 3) Configure a base da API, se necessário
echo "VITE_API_BASE=http://localhost:8080" > .env

# 4) Rode o dev server
npm run dev
```

Acesse: http://localhost:5173

## Endpoints esperados
- `GET/POST/PUT/DELETE /categorias`
- `GET/POST/PUT/DELETE /produtos`

## Observações
- IDs são automáticos no backend.
- Tipos `Categoria` e `Produto` estão em `src/types`.
- Serviços HTTP usam Axios com base em `VITE_API_BASE`.