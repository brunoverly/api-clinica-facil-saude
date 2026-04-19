package br.com.ClinicaFacilSaude.medico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicoRequestDto(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @Email(message = "Campo obrigatório")
        String email,
        @NotNull(message = "Campo obrigatório")
        @Size(min = 4, max = 20)
        String crm,
        @NotBlank(message = "Campo obrigatório")
        String especialidade
) {
}
