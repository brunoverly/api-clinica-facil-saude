package br.com.ClinicaFacilSaude.paciente.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PacienteRequestDto(
        @Schema(example = "Carlos Marques")
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @Schema(example = "carlos@email.com")
        @Email(message = "Campo obrigatório")
        String email,
        @Schema(example = "85465943214")
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 11, max = 11, message = "Campo deve ter exatamente 11 dígitos")
        String cpf,
        @Schema(example = "31987654321")
        @Size(max = 20)
        String telefone
) {
}
