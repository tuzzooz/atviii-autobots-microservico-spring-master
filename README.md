
- **Linguagem:** Java 17
- **Build:** Maven (usando `mvnw.cmd` — wrapper incluso)

**Pré-requisitos**:
- Java 17 instalado e disponível no `PATH`.
- (Opcional) Plugin Lombok na IDE para reduzir warnings ao editar classes com anotações Lombok.

**Como executar (PowerShell)**
- Abra o PowerShell e navegue até a pasta do projeto:

```
cd "automanager"
```

- Executar testes:

```
.\mvnw.cmd test
```

- Iniciar a aplicação (dev):

```
.\mvnw.cmd spring-boot:run
# A aplicação ficará disponível em http://localhost:8080
```

**Build (gerar JAR)**

```
.\mvnw.cmd -DskipTests package
# O JAR será gerado em: target\automanager-0.0.1-SNAPSHOT.jar
```

**Base URL**
- `http://localhost:8080`

**Rotas / Endpoints (exemplos de recursos)**

As rotas abaixo refletem os controllers atuais do projeto. Alguns controllers usam o caminho base singular (por exemplo `/empresa`) e expõem a listagem em `/empresa/empresas`.

**Empresa** (`EmpresaControle`)
- `GET /empresa/empresas` : listar empresas (retorna `200 OK` ou `404 NOT_FOUND` quando vazio).
- `GET /empresa/{id}` : obter empresa por id (`200 OK` ou `404 NOT_FOUND`).
- `POST /empresa/cadastro` : criar empresa. Exemplo body (JSON):

```json
{ "razaoSocial": "Razão", "nomeFantasia": "Fantasia", "cadastro": "2025-01-01" }
```
- `PUT /empresa/atualizar` : atualizar empresa (body com `id`).
- `DELETE /empresa/excluir/{id}` : excluir empresa.

**Mercadoria** (`MercadoriaControle`)
- `GET /mercadoria/mercadorias` : listar mercadorias (`200 OK` ou `204 NO_CONTENT`).
- `GET /mercadoria/{id}` : obter mercadoria por id.
- `POST /mercadoria/cadastro` : criar mercadoria. Exemplo body:

```json
{ "nome": "Produto A", "descricao": "...", "quantidade": 10, "valor": 99.9 }
```
- `PUT /mercadoria/atualizar` : atualizar mercadoria (body com `id`).
- `DELETE /mercadoria/excluir/{id}` : excluir mercadoria.

**Serviço** (`ServicoControle`)
- `GET /servico/servicos` : listar serviços (`200 OK` ou `204 NO_CONTENT`).
- `GET /servico/{id}` : obter serviço por id.
- `POST /servico/cadastro` : criar serviço. Exemplo body:

```json
{ "nome": "Troca de óleo", "descricao": "Serviço X", "valor": 120.0 }
```
- `PUT /servico/atualizar` : atualizar serviço.
- `DELETE /servico/excluir/{id}` : excluir serviço.

**Usuários** (`UsuarioControle`) — (base ajustada para `/usuarios`)
- `GET /usuarios` : listar usuários (`200 OK` ou `204 NO_CONTENT` quando vazio).
- `GET /usuarios/{id}` : obter usuário por id (`200 OK` ou `404_NOT_FOUND`).
- `POST /usuarios/cadastro` : cadastrar usuário. Exemplo body:

```json
{ "nome": "João Silva", "nomeSocial": "J. Silva", "dataNascimento": "1990-01-01" }
```
- `PUT /usuarios/atualizar` : atualizar usuário (body com `id`).
- `DELETE /usuarios/excluir/{id}` : excluir usuário (retorna `204 NO_CONTENT` quando removido).

**Veículos** (`VeiculoControle`)
- `GET /veiculo/veiculos` : listar veículos (`200 OK` ou `404_NOT_FOUND`).
- `GET /veiculo/{id}` : obter veículo por id (`200 OK` ou `404_NOT_FOUND`).
- `POST /veiculo/cadastro` : cadastrar veículo. Exemplo body:

```json
{ "modelo": "Fiat Uno", "placa": "ABC-1234", "tipo": "CARRO" }
```
- `PUT /veiculo/atualizar` : atualizar veículo.
- `DELETE /veiculo/excluir/{id}` : excluir veículo.

**Vendas** (`VendaControle`)
- `GET /venda/vendas` : listar vendas (`200 OK` ou `404_NOT_FOUND`).
- `GET /venda/{id}` : obter venda por id.
- `POST /venda/cadastro` : criar venda. Exemplo body (relacionamentos por id):

```json
{ "identificacao": "VEND-001", "cliente": { "id": 1 }, "funcionario": { "id": 2 }, "veiculo": { "id": 3 } }
```
- `PUT /venda/atualizar` : atualizar venda.
- `DELETE /venda/excluir/{id}` : excluir venda.

**Observações**
- Use `mvnw.cmd` no Windows para garantir compatibilidade da versão do Maven.
- Mensagens de erro e códigos HTTP seguem convenções REST (por exemplo, `404` para não encontrado, `409` para conflito quando identificador é fornecido indevidamente no POST, `204` para respostas sem corpo).
- Para desenvolvimento, recomenda-se habilitar o plugin Lombok na sua IDE para evitar warnings visuais; o projeto já inclui Lombok como dependência.

