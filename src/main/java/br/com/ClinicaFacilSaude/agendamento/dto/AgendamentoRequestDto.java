package br.com.ClinicaFacilSaude.agendamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoRequestDto(
        @NotNull(message = "Campo obrigatório")
        @Schema(example = "1")
        Long medicoId,
        @NotNull(message = "Campo obrigatório")
        @Schema(example = "22")
        Long pacienteId,
        @NotNull(message = "Campo Obrigatório")
        @Schema(example = "2026-04-16T16:00:00")
        LocalDateTime dataAgendamento,
        @Schema(example = " ")
        String motivoCancelamento
) {
}
