# 🍽️ Gerenciador de Produtos Domésticos

Um CRUD completo para gerenciar **Produtos** e **Categorias**, com **backend em Spring Boot** e **frontend em Vue 3 + Vite + TypeScript + Vuetify**.  

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

---

## 🔧 Configuração Inicial

### 1. Backend (Spring Boot)

```bash
cd spring_back

# Configure o banco de dados
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edite o application.properties com suas configurações de banco

# Configure as variáveis de ambiente
cp .env.example .env
# Edite o .env com uma chave JWT forte

# Execute
mvn spring-boot:run
```

### 2. Frontend (Vue.js)

```bash
cd vue_front

# Instale as dependências
npm install

# Execute em modo desenvolvimento
npm run dev
```

---

## 🔐 Segurança

### ⚠️ IMPORTANTE: Arquivos NÃO versionados
- `spring_back/src/main/resources/application.properties` - Configurações de banco
- `spring_back/.env` - Chave JWT e variáveis sensíveis
- `vue_front/.env` - Configurações do frontend

### ✅ Arquivos de exemplo (versionados)
- `spring_back/src/main/resources/application.properties.example`
- `spring_back/.env.example`
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





