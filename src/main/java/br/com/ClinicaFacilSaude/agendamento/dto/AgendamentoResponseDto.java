package br.com.ClinicaFacilSaude.agendamento.dto;

import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteResponseDto;

import java.time.LocalDateTime;

public record AgendamentoResponseDto(
        Long id,
        MedicoResponseDto medico,
        PacienteResponseDto paciente,
        LocalDateTime dataAgendamento,
        String status,
        String motivoCancelamento){
}
