package br.com.ClinicaFacilSaude.Auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(
        @Schema(description = "Email do usuário", example = "bruno@email.com")
        @Email(message = "Campo obrigatório")
        String email,
        @Schema(description = "Senha do usuário", example = "123")
        @NotBlank(message = "Campo obrigatório")
        String senha
) {
}
