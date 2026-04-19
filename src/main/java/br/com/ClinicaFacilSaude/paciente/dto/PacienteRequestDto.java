package br.com.ClinicaFacilSaude.paciente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PacienteRequestDto(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @Email(message = "Campo obrigatório")
        String email,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 11, max = 11, message = "Campo deve ter exatamente 11 dígitos")
        String cpf,
        @Size(max = 20)
        String telefone
) {
}
