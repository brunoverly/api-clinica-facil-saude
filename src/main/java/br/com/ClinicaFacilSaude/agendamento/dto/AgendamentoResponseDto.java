package br.com.ClinicaFacilSaude.agendamento.dto;

import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AgendamentoResponseDto(
        @Schema(example = "12")
        Long id,
        @Schema(example = "1")
        MedicoResponseDto medico,
        @Schema(example = "22")
        PacienteResponseDto paciente,
        @Schema(example = "2026-04-16T16:00:00")
        LocalDateTime dataAgendamento,
        @Schema(example = "AGENDADO")
        String status,
        @Schema(example = " ")
        String motivoCancelamento){
}
