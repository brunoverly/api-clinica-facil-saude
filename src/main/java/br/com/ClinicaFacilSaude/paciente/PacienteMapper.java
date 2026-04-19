package br.com.ClinicaFacilSaude.paciente;


import br.com.ClinicaFacilSaude.paciente.dto.PacienteRequestDto;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    Paciente toEntity (PacienteRequestDto dto);
    PacienteResponseDto toResponse(Paciente Paciente);
    List<PacienteResponseDto> toListResponse(List<Paciente> Paciente);
}

