package br.com.ClinicaFacilSaude.agendamento;

import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoRequestDto;
import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {
    AgendamentoResponseDto toResponse(Agendamento agendamento);
    Agendamento toEntity(AgendamentoRequestDto agendamentoRequestDto);
    List<AgendamentoResponseDto> toResponseList(List<Agendamento> agendamentos);
}
