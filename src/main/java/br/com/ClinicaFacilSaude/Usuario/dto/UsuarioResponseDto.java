package br.com.ClinicaFacilSaude.Usuario.dto;

import br.com.ClinicaFacilSaude.Usuario.UsuarioRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioResponseDto(
        @Schema(description = "ID do usuário", example = "1")
        Long id,
        @Schema(description = "Nome do usuário", example = "Bruno Martins")
        String nome,
        @Schema(description = "Email do usuário", example = "bruno@email.com")
        String email,
        @Schema(description = "ADMIN ou USER", example = "USER")
        UsuarioRole role
) {
}
