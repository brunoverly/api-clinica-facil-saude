package br.com.ClinicaFacilSaude.medico.dto;

public record MedicoResponseDto(
        Long id,
        String nome,
        String email,
        String crm,
        String especialidade
) {
}
