package br.com.ClinicaFacilSaude.paciente;

import br.com.ClinicaFacilSaude.exception.ConflitoDeDadosException;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteRequestDto;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {
    @Mock
    private PacienteRepository repository;
    
    @Mock
    private PacienteMapper mapper;
    
    @InjectMocks
    private PacienteService service;
    
    private Paciente createPaciente() {
        return Paciente.builder()
                .id(1L)
                .nome("Bruno Martins")
                .telefone("31988776655")
                .email("brunomartins@email.com")
                .cpf("987654321")
                .ativo(true)
                .build();
    }
    
    private PacienteRequestDto createPacienteRequestDtoV1() {
        return new PacienteRequestDto(
                "Bruno Martins",
                "brunomartins@email.com",
                "987654321",
                "31988776655"
        );
    }
    private PacienteRequestDto createPacienteRequestDtoV2() {
        return new PacienteRequestDto(
                "Carla Aparecida",
                "carlaaparecida@email.com",
                "12345678",
                "3166559988"
        );
    }
    private PacienteResponseDto createPacienteResponseDtoV1() {
        return new PacienteResponseDto(
                1L,
                "Bruno Martins",
                "brunomartins@email.com",
                "987654321",
                "31988776655"
        );
    }
    private PacienteResponseDto createPacienteResponseDtoV2() {
        return new PacienteResponseDto(
                1l,
                "Carla Aparecida",
                "carlaaparecida@email.com",
                "12345678",
                "3166559988"
        );
    }


    @Test
    void deveCriarPacienteComSucesso() {
        //ARRANGE
        Paciente paciente = createPaciente();
        PacienteRequestDto dtoRequest = createPacienteRequestDtoV1();
        PacienteResponseDto dtoResponse = createPacienteResponseDtoV1();

        //ACT
        when(repository.existsByEmailOrCpf(dtoRequest.email(), dtoRequest.cpf())).thenReturn(false);
        when(repository.save(paciente)).thenReturn(paciente);
        when(mapper.toEntity(dtoRequest)).thenReturn(paciente);
        when(mapper.toResponse(paciente)).thenReturn(dtoResponse);

        PacienteResponseDto response = service.create(dtoRequest);


        //ASSERT
        assertEquals(response, dtoResponse);
        verify(repository).existsByEmailOrCpf(dtoRequest.email(), dtoRequest.cpf());
        verify(mapper).toEntity(dtoRequest);
        verify(repository).save(paciente);
        verify(mapper).toResponse(paciente);
    }

    @Test
    void deveCriarPacienteComErro() {
        //ARRANGE
        PacienteRequestDto dtoRequest = createPacienteRequestDtoV1();
        //ACT
        when(repository.existsByEmailOrCpf(dtoRequest.email(), dtoRequest.cpf())).thenReturn(true);

        //ASSERT
        assertThrows(ConflitoDeDadosException.class, () -> service.create(dtoRequest));
        verify(repository).existsByEmailOrCpf(dtoRequest.email(), dtoRequest.cpf());
        verify(repository, never()).save(any());
    }
    
    @Test
    void deveAtualizarPacienteComSucesso() {
        //ARRANGE
        Long id = 1L;
        Paciente paciente = createPaciente();
        PacienteRequestDto dtoRequest = createPacienteRequestDtoV2();
        PacienteResponseDto dtoResponse = createPacienteResponseDtoV2();
        
        //ACT
        when(repository.findByIdAndAtivoTrue(id)).thenReturn(Optional.of(paciente));
        when(mapper.toResponse(paciente)).thenReturn(dtoResponse);
        when(repository.save(paciente)).thenReturn(paciente);
        PacienteResponseDto response = service.update(id,dtoRequest);

        //ASSERT
        assertEquals(response, dtoResponse);
        verify(repository).findByIdAndAtivoTrue(id);
        verify(mapper).toResponse(paciente);
        verify(repository).save(paciente);
    }

    @Test
    void deveAtualizarPacienteComErro() {
        //ARRANGE
        Long id = 1L;
        PacienteRequestDto dtoRequest = createPacienteRequestDtoV1();

        //ACT
        when(repository.findByIdAndAtivoTrue(id)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(EntityNotFoundException.class, () -> service.update(id, dtoRequest));
        verify(repository).findByIdAndAtivoTrue(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarPacienteComSucesso() {
        //ARRANGE
        Long id = 1L;
        Paciente paciente = createPaciente();

        //ACT
        when(repository.findByIdAndAtivoTrue(id)).thenReturn(Optional.of(paciente));
        when(repository.save(paciente)).thenReturn(paciente);
        service.delete(id);


        //ASSERT
        assertFalse(paciente.isAtivo());
        verify(repository).findByIdAndAtivoTrue(id);
        verify(repository).save(paciente);
    }

    @Test
    void deveDeletarPacienteComErro() {
        //ARRANGE
        Long id = 1L;

        //ACT
        when(repository.findByIdAndAtivoTrue(id)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(EntityNotFoundException.class, () -> service.delete(id));
        verify(repository).findByIdAndAtivoTrue(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deveListarPacientesComSucesso() {
        //ARRANGE
        Pageable pageable = PageRequest.of(0, 10);

        Paciente paciente = createPaciente();
        PacienteResponseDto dtoResponse = createPacienteResponseDtoV1();

        Page<Paciente> pacientes = new PageImpl<>(List.of(paciente));

        //ACT
        when(repository.findAllByAtivoTrue(pageable)).thenReturn(pacientes);
        when(mapper.toResponse(paciente)).thenReturn(dtoResponse);

        Page<PacienteResponseDto> response = service.findAll(pageable);

        assertEquals(1, response.getTotalElements());
        assertEquals(dtoResponse, response.getContent().get(0));
        verify(repository).findAllByAtivoTrue(pageable);
        verify(mapper).toResponse(paciente);
    }
}
