package br.com.ClinicaFacilSaude.paciente.dto;

public record PacienteResponseDto(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone
) {
}
