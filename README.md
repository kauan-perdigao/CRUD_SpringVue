# 🍽️ Gerenciador de Produtos Domésticos 

Um CRUD completo para gerenciar **Produtos** e **Categorias**, com **backend em Spring Boot** e **frontend em Vue 3 + Vite + TypeScript + Vuetify**.

## 🔍 Recursos de Busca e Filtros

### Busca de Produtos
- **Busca em tempo real** enquanto você digita
- **Filtro por nome** do produto
- **Debounce** para evitar muitas requisições
- **Paginação** integrada com a busca
- **Indicador visual** durante o carregamento

### Paginação
- Opções: 5, 10 ou 20 produtos por página
- Navegação entre páginas
- Contador total de itens
- Mantém filtros aplicados durante navegação

---

## 📊 Endpoints da API (busca em tempo real)
- 📄 **Paginação** de resultados (5, 10 ou 20 itens por página)
- 💰 **Formatação de preços** em Real (R$)
- 🏷️ **Associação** produto-categoria
- 🎨 **Interface moderna** com Vuetify Material Design
- ⚡ **Responsivo** - funciona em desktop e mobileomésticos  

---

## 🚀 Tecnologias

### Backend
- Java 21+
- Spring Boot 3
- Spring Data JPA (Hibernate)
- Maven
- PostgreSQL

### Frontend
- Vue 3 + Vite
- TypeScript
- Vuetify 3 (Material Design)
- Axios
- Vue Router

---

## ⚙️ Funcionalidades
- � **CRUD de Produtos** (Criar, Listar, Editar, Excluir)
- 🗂️ **CRUD de Categorias** (Criar, Listar, Editar, Excluir)
- � **Filtro de busca** por nome de produto
- 📄 **Paginação** de resultados
- 💰 **Formatação de preços** em Real (R$)
- 🏷️ **Associação** produto-categoria

---

## 🔧 Configuração Inicial

### 1. Backend (Spring Boot)

```bash
cd spring_back

# Configure o banco de dados
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edite o application.properties com suas configurações de banco PostgreSQL

# Execute
mvn spring-boot:run
```

**Backend rodará em:** `http://localhost:8080`

### 2. Frontend (Vue.js)

```bash
cd vue_front

# Instale as dependências
npm install

# Execute em modo desenvolvimento
npm run dev
```

**Frontend rodará em:** `http://localhost:5173`

---

## � Endpoints da API

### Produtos
- `GET /produtos` - Listar produtos (com busca e paginação)
- `POST /produtos` - Criar produto
- `PUT /produtos/{id}` - Atualizar produto
- `DELETE /produtos/{id}` - Deletar produto

### Categorias
- `GET /categorias` - Listar categorias
- `POST /categorias` - Criar categoria
- `PUT /categorias/{id}` - Atualizar categoria
- `DELETE /categorias/{id}` - Deletar categoria

---

## 🔧 Configuração

### ✅ Arquivo de exemplo (versionado)
- `spring_back/src/main/resources/application.properties.example`
- 🎨 Interface responsiva com Vuetify
- 🖼️ Ícone (favicon) personalizado
- ⚡ API REST integrada

---

## ▶️ Como rodar

### 1. Backend (Spring Boot)

**Pré-requisitos:**
- Java 17+
- Maven
- Banco configurado (por padrão H2 em memória) PostgreSQL

**Execução:**
```bash
cd spring_back
./mvnw spring-boot:run
```

### 1. Frontend (Vue)

**Pré-requisitos:**
- Vue 3
- Vuetify 3
- TypeScript
- Vite

**Execução:**
```bash
cd vue_front
npm run dev
```





