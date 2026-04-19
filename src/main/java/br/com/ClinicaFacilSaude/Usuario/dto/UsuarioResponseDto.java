package br.com.ClinicaFacilSaude.Usuario.dto;

import br.com.ClinicaFacilSaude.Usuario.UsuarioRole;
import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioResponseDto(
        @Schema(example = "1")
        Long id,
        @Schema(example = "Bruno Martins")
        String nome,
        @Schema(example = "bruno@email.com")
        String email,
        @Schema(example = "USER")
        UsuarioRole role
) {
}
