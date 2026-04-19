package br.com.ClinicaFacilSaude.medico;

import br.com.ClinicaFacilSaude.exception.ConflitoDeDadosException;
import br.com.ClinicaFacilSaude.medico.dto.MedicoRequestDto;
import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {
    @Mock
    private MedicoRepository repository;

    @Mock
    private MedicoMapper mapper;

    @InjectMocks
    private MedicoService service;

    private Medico criarMedico() {
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNome("João Silva");
        medico.setEmail("joaosilva@email.com");
        medico.setCrm("12345678");
        medico.setEspecialidade(Especialidade.CARDIOLOGIA);
        medico.setAtivo(true);
        return medico;
    }
    private MedicoResponseDto criarMedicoResponseDtoV1() {
        MedicoResponseDto dtoResponse = new MedicoResponseDto(
                1L,
                "João Silva",
                "joaosilva@email.com",
                "12345678",
                "CARDIOLOGIA"
        );
        return dtoResponse;
    }
    private MedicoResponseDto criarMedicoResponseDtoV2() {
        MedicoResponseDto dtoResponse = new MedicoResponseDto(
                1L,
                "Carlos Barbosa",
                "carlosbarbosa@email.com",
                "35879641",
                "ORTOPEDIA"
        );
        return dtoResponse;
    }


    private MedicoRequestDto criarMedicoRequestDtoV1() {
        MedicoRequestDto dtoRequest = new MedicoRequestDto(
                "João Silva",
                "joaosilva@email.com",
                "12345678",
                "CARDIOLOGIA"
        );
        return dtoRequest;
    }
    private MedicoRequestDto criarMedicoRequestDtoV2() {
        MedicoRequestDto dtoRequest = new MedicoRequestDto(
                "Carlos Barbosa",
                "carlosbarbosa@email.com",
                "35879641",
                "ORTOPEDIA"
        );
        return dtoRequest;
    }


    @Test
    void deveCriarMedicoComSucesso() {
        // ARRANGE
        MedicoRequestDto dto = criarMedicoRequestDtoV1();
        Medico medico = criarMedico();

        MedicoResponseDto responseDto = mock(MedicoResponseDto.class);

        when(repository.existsByEmailOrCrm(dto.email(), dto.crm())).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(medico);
        when(repository.save(medico)).thenReturn(medico);
        when(mapper.toResponse(medico)).thenReturn(responseDto);

        // ACT
        MedicoResponseDto response = service.create(dto);

        // ASSERT
        assertEquals(responseDto, response);
        verify(repository).existsByEmailOrCrm(dto.email(), dto.crm());
        verify(mapper).toEntity(dto);
        verify(repository).save(medico);
        verify(mapper).toResponse(medico);
    }

    @Test
    void deveCriarMedicoComErroDeEmailOuCrm() {
        //ARRANGE
        MedicoRequestDto dto = criarMedicoRequestDtoV1();

        //ACT
        when(repository.existsByEmailOrCrm(dto.email(), dto.crm())).thenReturn(true);

        //ASSERT
        assertThrows(ConflitoDeDadosException.class, () -> service.create(dto));
        verify(repository, never()).save(any());
    }





    @Test
    void deveDeletarMedicoComSucesso() {

        //ARRANGE
        Long id = 1L;
        Medico medico = criarMedico();

        //ACT
        when(repository.findByIdAndAtivoTrue(id)).thenReturn(Optional.of(medico));
        when(repository.save(medico)).thenReturn(medico);
        service.delete(id);

        //ASSERT
        verify(repository).findByIdAndAtivoTrue(id);
        verify(repository).save(medico);

    }

    @Test
    void deveDeletarMedicoComErro() {

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
    void deveListarMedicosAtivosComSucesso() {
        //ARRANGE
        Pageable pageable = PageRequest.of(0, 10);

        Medico medico = criarMedico();

        MedicoResponseDto dto = criarMedicoResponseDtoV1();

        Page<Medico> pageMedicos = new PageImpl<>(List.of(medico));

        //ACT
        when(repository.findAllByAtivoTrue(pageable)).thenReturn(pageMedicos);
        when(mapper.toResponse(medico)).thenReturn(dto);

        Page<MedicoResponseDto> response = service.findAll(pageable);

        //ASSERT
        assertEquals(1, response.getTotalElements());
        assertEquals(dto, response.getContent().get(0));

        verify(repository).findAllByAtivoTrue(pageable);
        verify(mapper).toResponse(medico);
    }

    @Test
    void deveAtualizarMedicoComSucesso() {
        //ARRANGE
        Medico medico = criarMedico();
        MedicoRequestDto dtoRequest = criarMedicoRequestDtoV2();
        MedicoResponseDto dtoResponse = criarMedicoResponseDtoV2();

        Long id = 1L;

        //ACT
        when(repository.findByIdAndAtivoTrue(id)).thenReturn(Optional.of(medico));
        when(repository.save(medico)).thenReturn(medico);
        when(mapper.toResponse(medico)).thenReturn(dtoResponse);

        service.update(id, dtoRequest);

        //ASSERT
        assertEquals(dtoRequest.nome(), medico.getNome());
        assertEquals(dtoRequest.email(), medico.getEmail());
        assertEquals(dtoRequest.crm(), medico.getCrm());
        assertEquals(Especialidade.ORTOPEDIA, medico.getEspecialidade());
        verify(repository).findByIdAndAtivoTrue(id);
        verify(repository).save(medico);
        verify(mapper).toResponse(medico);
    }

    @Test
    void deveAtualizarMedicoComErro() {

        //ARRANGE
        MedicoRequestDto dtoRequest = criarMedicoRequestDtoV1();
        Long id = 1L;

        //ACT
        when(repository.findByIdAndAtivoTrue(id)).thenReturn(Optional.empty());

        //ASSERT
        assertThrows(EntityNotFoundException.class, () -> service.update(id, dtoRequest));
        verify(repository).findByIdAndAtivoTrue(id);
        verify(repository, never()).save(any());

    }
}
