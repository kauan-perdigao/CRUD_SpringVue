# 🍽️ Gerenciador de Produtos Domésticos

Um CRUD completo para gerenciar **Produtos** e **Categorias**, com **backend em Spring Boot** e **frontend em Vue 3 + Vite + TypeScript + Vuetify**.  
Inclui autenticação com **cadastro/login de usuários (JWT)** e proteção de rotas.

---

## 🚀 Tecnologias

### Backend
- Java 17+
- Spring Boot 3
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- Maven
- Banco: H2 (dev) ou PostgreSQL/MySQL (prod)

### Frontend
- Vue 3 + Vite
- TypeScript
- Vuetify 3 (Material Design)
- Axios
- Vue Router

---

## ⚙️ Funcionalidades
- 🔑 **Login e cadastro de usuários**
- 📦 CRUD de **Produtos**
- 🗂️ CRUD de **Categorias**
- 🔒 Proteção de rotas (somente usuários autenticados acessam o CRUD)
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





