<div align="center">

# 🏥 ClínicaFácil Saúde Digital

<!-- Badges -->
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 3](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-8DCE71?style=for-the-badge)

---

> **Desafio Backend Junior:** Desenvolvimento de uma API RESTful escalável e segura para gerenciamento de agendamentos de consultas médicas, com forte ênfase em regras de negócio e testes unitários.

</div>

<br>

## 📋 Sobre o Projeto
O sistema **ClínicaFácil Saúde Digital** foi idealizado para gerenciar pacientes, médicos e o fluxo complexo de agendamentos. O coração da aplicação é a validação estrita de horários, disponibilidade e regras operacionais da clínica, garantindo a integridade dos dados antes de qualquer persistência no banco de dados.

## ⚙️ Funcionalidades e Regras de Negócio

O método de agendamento (`agendarConsulta()`) valida as seguintes regras críticas em cadeia:

- [x] **Horário Comercial:** Agendamentos permitidos apenas de Segunda a Sábado, das 07h às 19h.
- [x] **Antecedência Mínima:** Consultas devem ser marcadas com pelo menos 30 minutos de antecedência.
- [x] **Conflito de Médico:** Um médico não pode ter dois agendamentos no mesmo horário.
- [x] **Conflito de Paciente:** Um paciente não pode ter dois agendamentos no mesmo dia.
- [x] **Validação de Ativos:** Não é possível agendar consultas com médicos ou pacientes inativos.
- [x] **Regras de Cancelamento:**
    - Exige antecedência mínima de 24 horas.
    - O motivo do cancelamento é obrigatório.
    - Consultas já canceladas não podem ser canceladas novamente.
- [x] **Datas Válidas:** A data/hora do agendamento deve ser sempre no futuro.

## 🧪 Testes Unitários
Um dos grandes focos deste projeto é a cobertura da camada de serviço utilizando **JUnit 5** e **Mockito**.
Os testes seguem o padrão **AAA (Arrange, Act, Assert)** para garantir que cada exceção customizada seja lançada corretamente quando uma regra de negócio for violada (ex: `MedicoInativoException`, `HorarioForaComercialException`).

## 🗄️ Estrutura de Dados (Entidades)

* **Médico:** `id`, `nome`, `email` (único), `crm` (único), `especialidade` (Enum), `ativo`.
* **Paciente:** `id`, `nome`, `email` (único), `cpf` (único), `telefone`, `ativo`.
* **Agendamento:** `id`, `medico` (FK), `paciente` (FK), `dataHora`, `status` (Enum), `motivoCancelamento`.

**Enums utilizados:**
* *Especialidade:* `CARDIOLOGIA`, `DERMATOLOGIA`, `ORTOPEDIA`, `GINECOLOGIA`
* *Status do Agendamento:* `AGENDADO`, `CANCELADO`, `REALIZADO`

## 🚀 Endpoints da API

Todos os endpoints são protegidos via **Spring Security + JWT**.

### 👨‍⚕️ Médicos
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/medicos` | Cadastrar novo médico |
| `GET` | `/medicos` | Listar médicos ativos (com paginação) |
| `PUT` | `/medicos/{id}` | Atualizar dados do médico |
| `DELETE` | `/medicos/{id}` | Inativar médico (Soft Delete) |

### 🧑 Pacientes
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/pacientes` | Cadastrar novo paciente |
| `GET` | `/pacientes` | Listar pacientes ativos (com paginação) |
| `PUT` | `/pacientes/{id}` | Atualizar dados do paciente |
| `DELETE` | `/pacientes/{id}` | Inativar paciente (Soft Delete) |

### 📅 Agendamentos
| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/agendamentos` | Agendar uma nova consulta |
| `GET` | `/agendamentos` | Listar agendamentos (com filtros e paginação) |
| `GET` | `/agendamentos/{id}` | Buscar agendamento específico por ID |
| `PATCH` | `/agendamentos/{id}/cancelar` | Cancelar consulta (exige motivo e 24h de antecedência) |

---
<div align="center">
  Desenvolvido com ☕ e muito código por <b>brunoverly</b>.
</div>