package br.com.ClinicaFacilSaude.agendamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDto(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotNull(message = "Campo obrigatório")
        Long medicoId,
        @NotNull(message = "Campo obrigatório")
        Long pacienteId,
        @NotNull(message = "Campo Obrigatório")
        LocalDateTime dataAgendamento,
        String motivoCancelamento
) {
}
