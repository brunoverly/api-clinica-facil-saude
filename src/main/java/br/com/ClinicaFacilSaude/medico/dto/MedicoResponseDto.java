package br.com.ClinicaFacilSaude.medico.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MedicoResponseDto(
        @Schema(example = "23")
        Long id,
        @Schema(example = "Bruno Martins")
        String nome,
        @Schema(example = "bruno@email.com")
        String email,
        @Schema(example = "10004-MG")
        String crm,
        @Schema(example = "CARDIOLOGIA")
        String especialidade
) {
}
