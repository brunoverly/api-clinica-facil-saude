package br.com.ClinicaFacilSaude.Auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponseDto(
        @Schema(example = "Bruno Martins")
        String usuario,
        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJGaW5hbmNlQXBwIiwic3ViIjoibWFyY2Vsb0BlbWFpbC5jb20iLCJyb2xlIjoiVVNFUiIsImV4cCI6MTc3NjYwODc3Nn0.3NB10NA11CdeY_G0bi8qlpcBSAtZ0_27PNLJkUHGth8")
        String BearerToken) {
}
