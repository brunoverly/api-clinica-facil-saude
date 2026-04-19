package br.com.ClinicaFacilSaude.agendamento;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AgendamentoSpecification {
    public static Specification<Agendamento> temMedico(Long medicoId) {
        return (root, query, cb) ->
                medicoId == null ? null :
                        cb.equal(
                                root.get("medico").get("id"),
                                medicoId
                        );
    }

    public static Specification<Agendamento> temPaciente(Long pacienteId) {
        return (root, query, cb) ->
                pacienteId == null ? null :
                        cb.equal(
                                root.get("paciente").get("id"),
                                + pacienteId
                        );
    }
    public static Specification<Agendamento> dataMaior(LocalDateTime dataIncio) {
        return (root, query, cb) ->
                dataIncio == null ? null :
                        cb.greaterThanOrEqualTo(root.get("dataAgendamento"), dataIncio);
    }
    public static Specification<Agendamento> dataMenor(LocalDateTime dataFim) {
        return (root, query, cb) ->
                dataFim == null ? null :
                        cb.lessThanOrEqualTo(root.get("dataAgendamento"), dataFim);
    }
}
