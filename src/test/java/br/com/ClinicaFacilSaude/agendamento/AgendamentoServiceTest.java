package br.com.ClinicaFacilSaude.agendamento;

import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoRequestDto;
import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoResponseDto;
import br.com.ClinicaFacilSaude.medico.Especialidade;
import br.com.ClinicaFacilSaude.medico.Medico;
import br.com.ClinicaFacilSaude.medico.MedicoRepository;
import br.com.ClinicaFacilSaude.medico.dto.MedicoRequestDto;
import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
import br.com.ClinicaFacilSaude.paciente.Paciente;
import br.com.ClinicaFacilSaude.paciente.PacienteRepository;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {
    @Mock
    private AgendamentoRepository repository;
    @Mock
    private MedicoRepository medicoRepository;
    @Mock
    private PacienteRepository pacienteRepository;
    @Mock
    private AgendamentoMapper mapper;
    @InjectMocks
    private AgendamentoService service;

    Medico medico = Medico.builder()
            .id(1L)
            .nome("João Silva")
            .email("joaosilva@email.com")
            .crm("12345678")
            .especialidade(Especialidade.CARDIOLOGIA)
            .ativo(true)
            .build();

    Paciente paciente = Paciente.builder()
            .id(1L)
            .nome("Bruno Martins")
            .telefone("31988776655")
            .email("brunomartins@email.com")
            .cpf("987654321")
            .ativo(true)
            .build();

    private Agendamento createAgendamento() {
        return Agendamento.builder()
                .id(1L)
                .medico(medico)
                .paciente(paciente)
                .dataAgendamento(LocalDateTime.now())
                .status(StatusAgendamento.AGENDADO)
                .motivoCancelamento("")
                .build();
    };

    private AgendamentoRequestDto createAgendamentoRequestDto() {
        return new AgendamentoRequestDto(
                1L,
                1L,
                LocalDateTime.now(),
                ""
        );
    }


    @Test
    void deveDeletaAgendamentoComErroDeIdNaoEncontrado(){
        //ARRANGE
        Long id = 1L;
        AgendamentoRequestDto dtoRequest = createAgendamentoRequestDto();
        //ACT
        when(repository.findById(id)).thenReturn(Optional.empty());

        //ASSERT
        EntityNotFoundException ex = assertThrowsExactly(
                EntityNotFoundException.class,
                () -> service.cancel(dtoRequest, id)
        );
        assertEquals("Agendamento com id {1} não localizado", ex.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }


    @Test
    void deveDeletaAgendamentoComSucesso() throws BadRequestException {
        //ARRANGE
        Long id = 1L;
        LocalDateTime dataTeste = LocalDateTime.now().plusDays(2);

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .medico(medico)
                .paciente(paciente)
                .dataAgendamento(dataTeste)
                .status(StatusAgendamento.AGENDADO)
                .motivoCancelamento("")
                .build();

        AgendamentoRequestDto dtoRequest = new AgendamentoRequestDto(
                1L,
                1L,
                dataTeste,
                "Imprevisto por parte do paciente"
        );

        //ACT
        when(repository.findById(id)).thenReturn(Optional.of(agendamento));
        service.cancel(dtoRequest, id);

        //ASSERT
        assertEquals(StatusAgendamento.CANCELADO, agendamento.getStatus());
        assertEquals(dtoRequest.motivoCancelamento(), agendamento.getMotivoCancelamento());
        verify(repository).findById(id);
        verify(repository).save(agendamento);
    }

    @Test
    void deveDeletaComErroDeMotivoCancelarVazio() throws BadRequestException {
        //ARRANGE
        Long id = 1L;
        LocalDateTime dataTeste = LocalDateTime.now().plusDays(2);

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .medico(medico)
                .paciente(paciente)
                .dataAgendamento(dataTeste)
                .status(StatusAgendamento.AGENDADO)
                .motivoCancelamento("")
                .build();

        AgendamentoRequestDto dtoRequest = new AgendamentoRequestDto(
                1L,
                1L,
                dataTeste,
                ""
        );

        //ACT
        when(repository.findById(id)).thenReturn(Optional.of(agendamento));
        //ASSERT
        BadRequestException ex = assertThrowsExactly(
                BadRequestException.class,
                () -> service.cancel(dtoRequest, id)
        );
        assertEquals("Motivo do cancelamento deve ser informado na requisição", ex.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }
    @Test
    void deveDeletaComErroDeAgendamentoJaCancelado() throws BadRequestException {
        //ARRANGE
        Long id = 1L;
        LocalDateTime dataTeste = LocalDateTime.now().plusDays(2);

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .medico(medico)
                .paciente(paciente)
                .dataAgendamento(dataTeste)
                .status(StatusAgendamento.CANCELADO)
                .motivoCancelamento("")
                .build();

        AgendamentoRequestDto dtoRequest = new AgendamentoRequestDto(
                1L,
                1L,
                dataTeste,
                "Imprevisto por parte do paciente"
        );

        //ACT
        when(repository.findById(id)).thenReturn(Optional.of(agendamento));
        //ASSERT
        BadRequestException ex = assertThrowsExactly(
                BadRequestException.class,
                () -> service.cancel(dtoRequest, id)
        );
        assertEquals("Agendamento com id {1} já consta como cancelado", ex.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletaComErroDeAntecedencia24hNaoRespeitado() throws BadRequestException {
        //ARRANGE
        Long id = 1L;
        LocalDateTime dataTeste = LocalDateTime.now().plusHours(3);

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .medico(medico)
                .paciente(paciente)
                .dataAgendamento(dataTeste)
                .status(StatusAgendamento.AGENDADO)
                .motivoCancelamento("")
                .build();

        AgendamentoRequestDto dtoRequest = new AgendamentoRequestDto(
                1L,
                1L,
                dataTeste,
                "Imprevisto por parte do paciente"
        );

        //ACT
        when(repository.findById(id)).thenReturn(Optional.of(agendamento));


        //ASSERT
        BadRequestException ex = assertThrowsExactly(
                BadRequestException.class,
                () -> service.cancel(dtoRequest, id)
        );
        assertEquals("O cancelamento não respeita o tempo mínimo de 24 horas", ex.getMessage());
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPorIdComSucesso() throws BadRequestException {
        //ARRANGE
        Long id = 1L;
        Agendamento agendamento = createAgendamento();
        MedicoResponseDto medicoResponse = new MedicoResponseDto(
                1L,
                "João Silva",
                "joaosilva@email.com",
                "12345678",
                "CARDIOLOGIA"
        );
        PacienteResponseDto pacienteResponse = new PacienteResponseDto(
                1L,
                "Bruno Martins",
                "31988776655",
                "brunomartins@email.com",
                "987654321"
        );
        AgendamentoResponseDto dtoResponse = new AgendamentoResponseDto(
                1L,
                medicoResponse,
                pacienteResponse,
                LocalDateTime.now().plusDays(3),
                "AGENDADO",
                ""
        );

        //ACT
        when(repository.findById(id)).thenReturn(Optional.of(agendamento));
        when(mapper.toResponse(agendamento)).thenReturn(dtoResponse);
        AgendamentoResponseDto response = service.findById(id);

        //ASSERT
        assertEquals(dtoResponse, response);
        verify(repository).findById(id);
        verify(mapper).toResponse(agendamento);
        verify(repository, never()).save(any());

    }
    @Test
    void deveBuscarPorIdComErroNaoEncontrado() throws BadRequestException {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(id));
        assertEquals("Agendamento com id {1} não localizado", ex.getMessage());
        verify(repository).findById(id);
        verify(mapper, never()).toResponse(any());
        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarTodosOsAgendamentosComSucesso(){
        //ARRANGE
        Agendamento agendamento = createAgendamento();
        MedicoResponseDto medicoResponse = new MedicoResponseDto(
                1L,
                "João Silva",
                "joaosilva@email.com",
                "12345678",
                "CARDIOLOGIA"
        );
        PacienteResponseDto pacienteResponse = new PacienteResponseDto(
                1L,
                "Bruno Martins",
                "31988776655",
                "brunomartins@email.com",
                "987654321"
        );
        AgendamentoResponseDto dtoResponse = new AgendamentoResponseDto(
                1L,
                medicoResponse,
                pacienteResponse,
                LocalDateTime.now().plusDays(3),
                "AGENDADO",
                ""
        );

        List<Agendamento> agendamentos = List.of(agendamento);


        when(repository.findAll(any(Specification.class))).thenReturn(agendamentos);
        when(mapper.toResponse(agendamento)).thenReturn(dtoResponse);

        //ACT
        List<AgendamentoResponseDto> responseDtos = service.findAll(1L,1L,null, null);

        //ASSERT
        assertEquals(1, responseDtos.size());
        assertEquals(dtoResponse, responseDtos.get(0));

        verify(repository).findAll(any(Specification.class));
        verify(mapper).toResponse(agendamento);
    }

    @Test
    void deveCadastrarAgendamentoComSucesso() throws BadRequestException {
        LocalDateTime dataTeste = LocalDateTime.now()
                .plusDays(2)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        Agendamento agendamento = Agendamento.builder()
                .id(1L)
                .medico(medico)
                .paciente(paciente)
                .dataAgendamento(dataTeste)
                .status(StatusAgendamento.AGENDADO)
                .motivoCancelamento("")
                .build();

        AgendamentoRequestDto dtoRequest = new AgendamentoRequestDto(
                1L,
                1L,
                dataTeste,
                ""
        );

        MedicoResponseDto medicoResponse = new MedicoResponseDto(
                1L,
                "João Silva",
                "joaosilva@email.com",
                "12345678",
                "CARDIOLOGIA"
        );
        PacienteResponseDto pacienteResponse = new PacienteResponseDto(
                1L,
                "Bruno Martins",
                "31988776655",
                "brunomartins@email.com",
                "987654321"
        );
        AgendamentoResponseDto dtoResponse = new AgendamentoResponseDto(
                1L,
                medicoResponse,
                pacienteResponse,
                dataTeste,
                "AGENDADO",
                ""
        );

        when(medicoRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(medico));
        when(pacienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(paciente));
        when(repository.medicoTemAgendamento(medico.getId(), StatusAgendamento.AGENDADO,dataTeste, dataTeste.plusHours(1))).thenReturn(false);
        when(repository.pacienteTemAgendamento(paciente.getId(), StatusAgendamento.AGENDADO,dataTeste, dataTeste.plusHours(1))).thenReturn(false);
        when(mapper.toResponse(any(Agendamento.class))).thenReturn(dtoResponse);
        when(repository.save(any(Agendamento.class))).thenReturn(agendamento);

        AgendamentoResponseDto response = service.create(dtoRequest);

        assertEquals(dtoResponse, response);
        verify(medicoRepository).findByIdAndAtivoTrue(1L);
        verify(pacienteRepository).findByIdAndAtivoTrue(1L);
        verify(repository).medicoTemAgendamento(medico.getId(), StatusAgendamento.AGENDADO, dataTeste, dataTeste.plusHours(1));
        verify(repository).pacienteTemAgendamento(paciente.getId(), StatusAgendamento.AGENDADO, dataTeste, dataTeste.plusHours(1));
        verify(mapper).toResponse(any(Agendamento.class));
        verify(repository).save(any(Agendamento.class));
    }

}
