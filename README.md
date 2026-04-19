<div align="center">

# 🏥 ClínicaFácil Saúde Digital

<!-- Badges Core & Spring -->
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT_Bearer-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

<!-- Badges Data & Utils -->
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC042D?style=for-the-badge&logo=lombok&logoColor=white)
![MapStruct](https://img.shields.io/badge/MapStruct-F29111?style=for-the-badge)

<!-- Badges Architecture & Tests -->
![DDD](https://img.shields.io/badge/Architecture-DDD-0052CC?style=for-the-badge)
![JUnit 5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-8DCE71?style=for-the-badge)
---
</div>

<br>

## 📋 Sobre o Projeto
O sistema **ClínicaFácil Saúde Digital** foi idealizado para gerenciar pacientes, médicos e o fluxo complexo de agendamentos. O coração da aplicação é a validação estrita de horários, disponibilidade e regras operacionais da clínica, garantindo a integridade dos dados antes de qualquer persistência no banco de dados.

---

## 🔄 Fluxo de Funcionamento

Para utilizar a API corretamente, é necessário seguir o fluxo lógico de negócio:

1. **Autenticação:**
  * Realize o login (ou cadastro de usuário do sistema) para obter o **Token JWT**.
  * Em todas as requisições subsequentes, adicione o header `Authorization: Bearer <seu_token>`.
2. **Cadastros Base (Médicos e Pacientes):**
  * Cadastre os **Médicos** informando nome, CRM, email e especialidade.
  * Cadastre os **Pacientes** informando nome, CPF, email e telefone.
3. **Agendamento de Consultas:**
  * De posse do `id` do médico e do `id` do paciente, realize o agendamento enviando a `dataHora` desejada.
  * A API irá passar por todas as validações de regras de negócio. Se tudo estiver correto, a consulta será salva com o status `AGENDADO`.

---

## 🛠️ Tecnologias e Ferramentas

O projeto foi construído utilizando as melhores práticas e ferramentas do ecossistema Java:

* **Java 17 & Spring Boot 3:** Base moderna, performática e robusta para a API.
* **Spring Security & JWT:** Autenticação e autorização via token Bearer.
* **Spring Data JPA & PostgreSQL:** Persistência de dados relacional.
* **Flyway Migrations:** Versionamento de banco de dados garantindo consistência entre ambientes.
* **MapStruct:** Mapeamento eficiente e type-safe entre Entidades e DTOs.
* **Lombok:** Redução de boilerplate code.
* **JUnit 5 & Mockito:** Frameworks para testes unitários focados na camada de serviço.

---

## 🏗️ Arquitetura e Padrões

### 📁 Domain-Driven Design (DDD)
A estrutura de pastas do projeto foi organizada baseada nos conceitos de **DDD**, separando claramente as responsabilidades:
* **Domain:** Entidades principais do negócio, Enums e exceções de domínio.
* **Application (Service):** Casos de uso e regras de negócio. Onde o `AgendamentoService` orquestra as validações em cadeia.
* **Infrastructure:** Configurações de segurança, tratamento de erros globais, integrações e persistência.
* **Web/Presentation:** Controllers REST e DTOs.

### ⚙️ Perfis de Configuração (Properties)
O projeto utiliza a separação de ambientes do Spring Boot para gerenciar configurações:
* `application.properties`: Configurações globais, secret do JWT e definições padrão.
* `application-dev.properties`: Configurações exclusivas para o ambiente de desenvolvimento local (ex: credenciais do banco local, logs do Hibernate).

---

## 🧪 Testes Unitários

Um dos grandes focos deste projeto é a cobertura da camada de serviço.

* Utilizando **JUnit 5** e **Mockito** (`@ExtendWith(MockitoExtension.class)`).
* Aplicação do padrão **AAA (Arrange, Act, Assert)** para configurar mocks, executar o método e validar o lançamento de exceções customizadas sem acessar o banco de dados.

---

## ⚙️ Regras de Negócio e Validações

O agendamento valida as seguintes regras críticas em cadeia antes da persistência:

- [x] **Horário Comercial:** Apenas de Segunda a Sábado, das 07h às 19h.
- [x] **Antecedência Mínima:** Consultas devem ser marcadas com pelo menos 30 minutos de antecedência.
- [x] **Conflito de Médico/Paciente:** Impede agendamentos no mesmo horário para o médico ou no mesmo dia para o paciente.
- [x] **Validação de Ativos:** Não é possível agendar com médicos ou pacientes inativos.
- [x] **Regras de Cancelamento:** Exige 24 horas de antecedência, motivo obrigatório e bloqueia duplo cancelamento.

---

## ▶️ Como Executar o Projeto

### Pré-requisitos
* Java 17 instalado
* Maven instalado
* PostgreSQL rodando na máquina local (porta 5432) ou via Docker

### Passo a passo

1. **Clone o repositório:**
```bash
git clone https://github.com/brunoverly/api-clinica-facil-saude.git
cd api-clinica-facil-saude
```

2. **Crie o banco de dados:**
   No seu PostgreSQL (via pgAdmin, DBeaver ou terminal), crie um banco de dados chamado `clinicafacil_db`.

3. **Configure as variáveis de ambiente:**
   Edite o arquivo `src/main/resources/application-dev.properties` e insira as suas credenciais do PostgreSQL:

```properties
# Configurações do Banco de Dados Local (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/clinicafacil_db
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres

# O Flyway rodará as migrations automaticamente ao iniciar
spring.flyway.enabled=true
```
*Certifique-se também de ter uma chave secreta configurada para o JWT no `application.properties`:*
```properties
api.security.token.secret=sua_chave_secreta_super_segura_aqui
```

4. **Rode o projeto:**
   Pelo terminal na raiz do projeto, execute:
```bash
mvn spring-boot:run
```
*(Ou execute a classe `ApiClinicaFacilSaudeApplication.java` diretamente na sua IDE de preferência como IntelliJ ou Eclipse).*

5. Ao rodar o projeto, o **Flyway** criará automaticamente as tabelas `medicos`, `pacientes` e `agendamentos` baseadas nos arquivos `.sql` presentes na pasta `db/migration`.

---

## 🚀 Endpoints da API

*Todos os endpoints listados abaixo exigem o token no Header `Authorization: Bearer <token>`.*

### 👨‍⚕️ Médicos
* `POST /medicos` - Cadastrar
* `GET /medicos` - Listar ativos
* `PUT /medicos/{id}` - Atualizar
* `DELETE /medicos/{id}` - Inativar (Soft Delete)

### 🧑 Pacientes
* `POST /pacientes` - Cadastrar
* `GET /pacientes` - Listar ativos
* `PUT /pacientes/{id}` - Atualizar
* `DELETE /pacientes/{id}` - Inativar (Soft Delete)

### 📅 Agendamentos
* `POST /agendamentos` - Agendar consulta
* `GET /agendamentos` - Listar com paginação
* `PATCH /agendamentos/{id}/cancelar` - Cancelar consulta

---
<div align="center">
  Desenvolvido com ☕ e muito código por <b>brunoverly</b>.
</div>