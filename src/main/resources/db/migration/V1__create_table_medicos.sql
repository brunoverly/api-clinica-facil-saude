CREATE TABLE medicos
(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(250),
    email VARCHAR(250) UNIQUE,
    crm  VARCHAR(20),
    especialidade VARCHAR(100),
    ativo BOOLEAN DEFAULT TRUE
)