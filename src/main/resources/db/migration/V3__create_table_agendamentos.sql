CREATE TABLE agendamentos
(
    id BIGSERIAL PRIMARY KEY,
    data_agendamento TIMESTAMP,
    status VARCHAR(25),
    motivo_cancelamento VARCHAR(250),
    medico_id BIGINT,
    paciente_id BIGINT,
    CONSTRAINT fk_agendamentos_medicos
        FOREIGN KEY (medico_id)
            REFERENCES medicos (id),
    CONSTRAINT fk_agendamentos_pacientes
        FOREIGN KEY (paciente_id)
            REFERENCES pacientes (id)
)