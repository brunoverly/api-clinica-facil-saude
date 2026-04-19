package br.com.ClinicaFacilSaude.medico;

import br.com.ClinicaFacilSaude.medico.dto.MedicoRequestDto;
import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
    Medico toEntity (MedicoRequestDto dto);
    MedicoResponseDto toResponse(Medico medico);
    List<MedicoResponseDto> toListResponse(List<Medico> medico);
}
