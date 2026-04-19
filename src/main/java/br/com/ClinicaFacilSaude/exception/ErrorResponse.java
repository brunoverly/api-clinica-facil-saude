package br.com.ClinicaFacilSaude.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ErrorResponse(
        @Schema(example = "2026-04-16T16:00:00")
        @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
        LocalDateTime timestamp,
        @Schema(example = "400")
        int status,
        @Schema(example = "BAD_REQUEST")
        String error,
        @Schema(example = "Erro ao processar a requisição")
        String message,
        @Schema(example = "/teste")
        String path
) {
}
