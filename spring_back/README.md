# Spring Boot CRUD - Backend

## 🚀 Configuração do Ambiente

### Pré-requisitos
- Java 21+
- PostgreSQL
- Maven

### Setup Inicial

1. **Clone o repositório**
   ```bash
   git clone <seu-repo>
   cd spring_back
   ```

2. **Configure as variáveis de ambiente**
   ```bash
   # Copie o arquivo de exemplo
   cp .env.example .env
   
   # Edite o .env com suas configurações reais
   ```

3. **Gere uma chave JWT forte**
   ```bash
   # Linux/Mac
   openssl rand -base64 32
   
   # Windows PowerShell
   $bytes = New-Object byte[] 32
   [System.Security.Cryptography.RNGCryptoServiceProvider]::Create().GetBytes($bytes)
   [Convert]::ToBase64String($bytes)
   ```

4. **Configure o banco de dados PostgreSQL**
   - Crie um banco chamado `crud`
   - Atualize as credenciais no arquivo `.env`

5. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

## 🔐 Segurança

- **NUNCA** versione o arquivo `.env`
- **SEMPRE** use chaves JWT fortes (32+ caracteres)
- **MANTENHA** as credenciais do banco seguras

## 📚 Endpoints da API

### Autenticação
- `POST /auth/register` - Registro de usuário
- `POST /auth/login` - Login de usuário

### Produtos (Requer autenticação)
- `GET /produtos` - Listar produtos
- `POST /produtos` - Criar produto
- `PUT /produtos/{id}` - Atualizar produto  
- `DELETE /produtos/{id}` - Deletar produto

### Categorias (Requer autenticação)
- `GET /categorias` - Listar categorias
- `POST /categorias` - Criar categoria
- `PUT /categorias/{id}` - Atualizar categoria
- `DELETE /categorias/{id}` - Deletar categoria