package br.com.ClinicaFacilSaude.agendamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>, JpaSpecificationExecutor<Agendamento> {

    @Query("""
    SELECT COUNT(a) > 0
    FROM Agendamento a
    WHERE a.medico.id = :id
      AND a.status = :statusAgendamento
      AND a.dataAgendamento BETWEEN :inicio AND :fim
    """)
    boolean medicoTemAgendamento(Long id, StatusAgendamento statusAgendamento, LocalDateTime inicio, LocalDateTime fim);

    @Query("""
    SELECT COUNT(a) > 0
    FROM Agendamento a
    WHERE a.paciente.id = :id
      AND a.status = :statusAgendamento
      AND a.dataAgendamento BETWEEN :inicio AND :fim
    """)
    boolean pacienteTemAgendamento(Long id, StatusAgendamento statusAgendamento, LocalDateTime inicio, LocalDateTime fim);
}
