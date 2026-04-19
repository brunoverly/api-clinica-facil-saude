package br.com.ClinicaFacilSaude.Usuario;

import br.com.ClinicaFacilSaude.Usuario.dto.UsuarioRequestDto;
import br.com.ClinicaFacilSaude.Usuario.dto.UsuarioResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity (UsuarioRequestDto dto);
    UsuarioResponseDto toResponse (Usuario usuario);
    List<UsuarioResponseDto> toResponseList (List<Usuario> usuarios);
}
