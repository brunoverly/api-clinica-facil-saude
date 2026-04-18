INSERT INTO medicos (nome, email, crm, especialidade, ativo) VALUES
                                                                 ('Dr. João Silva', 'joao@clinica.com', '10001-MG', 'CARDIOLOGIA', true),
                                                                 ('Dra. Maria Souza', 'maria@clinica.com', '10002-MG', 'DERMATOLOGIA', true),
                                                                 ('Dr. Pedro Lima', 'pedro@clinica.com', '10003-MG', 'ORTOPEDIA', true),
                                                                 ('Dra. Ana Costa', 'ana@clinica.com', '10004-MG', 'GINECOLOGISTA', true),
                                                                 ('Dr. Carlos Mendes', 'carlos@clinica.com', '10005-MG', 'CARDIOLOGIA', true),
                                                                 ('Dra. Paula Ribeiro', 'paula@clinica.com', '10006-MG', 'DERMATOLOGIA', true),
                                                                 ('Dr. Marcos Oliveira', 'marcos@clinica.com', '10007-MG', 'ORTOPEDIA', true),
                                                                 ('Dra. Juliana Rocha', 'juliana@clinica.com', '10008-MG', 'GINECOLOGISTA', true),
                                                                 ('Dr. André Martins', 'andre@clinica.com', '10009-MG', 'CARDIOLOGIA', true),
                                                                 ('Dra. Fernanda Alves', 'fernanda@clinica.com', '10010-MG', 'DERMATOLOGIA', true);

INSERT INTO pacientes (nome, email, cpf, telefone, ativo) VALUES
                                                              ('Bruno Souza', 'bruno@email.com', '12345678901', '31999999999', true),
                                                              ('Lucas Pereira', 'lucas@email.com', '23456789012', '31988888888', true),
                                                              ('Fernanda Lima', 'fernanda@email.com', '34567890123', '31977777777', true),
                                                              ('Juliana Alves', 'juliana@email.com', '45678901234', '31966666666', true),
                                                              ('Rafael Gomes', 'rafael@email.com', '56789012345', '31955555555', true),
                                                              ('Camila Santos', 'camila@email.com', '67890123456', '31944444444', true),
                                                              ('Thiago Costa', 'thiago@email.com', '78901234567', '31933333333', true),
                                                              ('Patricia Nunes', 'patricia@email.com', '89012345678', '31922222222', true),
                                                              ('Eduardo Teixeira', 'eduardo@email.com', '90123456789', '31911111111', true),
                                                              ('Larissa Mendes', 'larissa@email.com', '11223344556', '31900000000', true);

INSERT INTO agendamentos (data_agendamento, status, motivo_cancelamento, medico_id, paciente_id) VALUES
                                                                                                     ('2026-04-20 08:00:00', 'AGENDADO', NULL, 1, 1),
                                                                                                     ('2026-04-20 09:00:00', 'REALIZADO', NULL, 2, 2),
                                                                                                     ('2026-04-20 10:00:00', 'CANCELADO', 'Paciente faltou', 3, 3),
                                                                                                     ('2026-04-20 11:00:00', 'AGENDADO', NULL, 4, 4),
                                                                                                     ('2026-04-20 13:00:00', 'REALIZADO', NULL, 5, 5),

                                                                                                     ('2026-04-21 08:30:00', 'AGENDADO', NULL, 6, 6),
                                                                                                     ('2026-04-21 09:30:00', 'REALIZADO', NULL, 7, 7),
                                                                                                     ('2026-04-21 10:30:00', 'CANCELADO', 'Reagendado', 8, 8),
                                                                                                     ('2026-04-21 11:30:00', 'AGENDADO', NULL, 9, 9),
                                                                                                     ('2026-04-21 14:00:00', 'REALIZADO', NULL, 10, 10),

                                                                                                     ('2026-04-22 08:00:00', 'AGENDADO', NULL, 1, 2),
                                                                                                     ('2026-04-22 09:00:00', 'REALIZADO', NULL, 2, 3),
                                                                                                     ('2026-04-22 10:00:00', 'CANCELADO', 'Problema pessoal', 3, 4),
                                                                                                     ('2026-04-22 11:00:00', 'AGENDADO', NULL, 4, 5),
                                                                                                     ('2026-04-22 13:00:00', 'REALIZADO', NULL, 5, 6),

                                                                                                     ('2026-04-23 08:00:00', 'AGENDADO', NULL, 6, 7),
                                                                                                     ('2026-04-23 09:00:00', 'REALIZADO', NULL, 7, 8),
                                                                                                     ('2026-04-23 10:00:00', 'CANCELADO', 'Médico indisponível', 8, 9),
                                                                                                     ('2026-04-23 11:00:00', 'AGENDADO', NULL, 9, 10),
                                                                                                     ('2026-04-23 13:00:00', 'REALIZADO', NULL, 10, 1),

                                                                                                     ('2026-04-24 08:00:00', 'AGENDADO', NULL, 1, 3),
                                                                                                     ('2026-04-24 09:00:00', 'REALIZADO', NULL, 2, 4),
                                                                                                     ('2026-04-24 10:00:00', 'CANCELADO', 'Paciente desistiu', 3, 5),
                                                                                                     ('2026-04-24 11:00:00', 'AGENDADO', NULL, 4, 6),
                                                                                                     ('2026-04-24 13:00:00', 'REALIZADO', NULL, 5, 7),

                                                                                                     ('2026-04-25 08:00:00', 'AGENDADO', NULL, 6, 8),
                                                                                                     ('2026-04-25 09:00:00', 'REALIZADO', NULL, 7, 9),
                                                                                                     ('2026-04-25 10:00:00', 'CANCELADO', 'Horário indisponível', 8, 10),
                                                                                                     ('2026-04-25 11:00:00', 'AGENDADO', NULL, 9, 1),
                                                                                                     ('2026-04-25 13:00:00', 'REALIZADO', NULL, 10, 2);