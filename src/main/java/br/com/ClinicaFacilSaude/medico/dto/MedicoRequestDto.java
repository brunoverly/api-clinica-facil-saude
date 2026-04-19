package br.com.ClinicaFacilSaude.medico.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicoRequestDto(
        @Schema(example = "Bruno Martins")
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @Schema(example = "bruno@email.com")
        @Email(message = "Campo obrigatório")
        String email,
        @Schema(example = "10004-MG")
        @NotNull(message = "Campo obrigatório")
        @Size(min = 4, max = 20)
        String crm,
        @Schema(example = "CARDIOLOGIA")
        @NotBlank(message = "Campo obrigatório")
        String especialidade
) {
}
