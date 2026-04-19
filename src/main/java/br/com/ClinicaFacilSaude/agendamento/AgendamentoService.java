package br.com.ClinicaFacilSaude.agendamento;

import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoRequestDto;
import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoResponseDto;
import br.com.ClinicaFacilSaude.exception.ConflitoDeDadosException;
import br.com.ClinicaFacilSaude.medico.Medico;
import br.com.ClinicaFacilSaude.medico.MedicoRepository;
import br.com.ClinicaFacilSaude.paciente.Paciente;
import br.com.ClinicaFacilSaude.paciente.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository repository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private AgendamentoMapper mapper;

    public AgendamentoResponseDto create(@Valid AgendamentoRequestDto dto) throws BadRequestException {
        verificarHorarioComercial(dto);


        Medico medico = medicoRepository.findByIdAndAtivoTrue(dto.medicoId()).orElseThrow(
                ()-> new EntityNotFoundException("Médico com id {" + dto.medicoId() + "} não localizado"));

        Paciente paciente = pacienteRepository.findByIdAndAtivoTrue(dto.pacienteId()).orElseThrow(
                ()-> new EntityNotFoundException("Paciente com id {" + dto.pacienteId() + "} não localizado"));

        verificarAgendaMedicoEPaciente(dto, medico, paciente);

        Agendamento agendamento = new Agendamento();
        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
        agendamento.setDataAgendamento(dto.dataAgendamento());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        repository.save(agendamento);

        return mapper.toResponse(agendamento);
    }

    public List<AgendamentoResponseDto> findAll(Long medico, Long paciente, LocalDateTime inicio, LocalDateTime fim) {
        Specification<Agendamento> spec = AgendamentoSpecification.temMedico(medico)
                .and(AgendamentoSpecification.temPaciente(paciente))
                .and(AgendamentoSpecification.dataMaior(inicio))
                .and(AgendamentoSpecification.dataMenor(fim));

        List<Agendamento> agendamentos = repository.findAll(spec);

        return agendamentos.stream().map(mapper::toResponse).toList();
    }

    public AgendamentoResponseDto findById(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento com id {" + id + "} não localizado"));
        return mapper.toResponse(agendamento);
    }

    public void cancel(AgendamentoRequestDto dto, Long id) throws BadRequestException {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento com id {" + id + "} não localizado"));

        verificarCancelamento(dto, agendamento);

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamento.setMotivoCancelamento(dto.motivoCancelamento());
        repository.save(agendamento);
    }

    private static void verificarCancelamento(AgendamentoRequestDto dto, Agendamento agendamento) throws BadRequestException {
        if(dto.motivoCancelamento() == null || dto.motivoCancelamento().isBlank()){
            throw new BadRequestException("Motivo do cancelamento deve ser informado na requisição");
        }
        if(agendamento.getStatus().equals(StatusAgendamento.CANCELADO)){
            throw new BadRequestException("Agendamento com id {" + agendamento.getId() + "} já consta como cancelado");
        }
        Duration duration = Duration.between(LocalDateTime.now(), agendamento.getDataAgendamento());
        if(duration.toHours() <= 24){
            throw new BadRequestException("O cancelamento não respeita o tempo mínimo de 24 horas");
        }
    }
    private void verificarAgendaMedicoEPaciente(AgendamentoRequestDto dto, Medico medico, Paciente paciente) throws BadRequestException {

        LocalDateTime inicio = dto.dataAgendamento().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fim = inicio.plusHours(1);

        boolean medicoTemAgendamento = repository.medicoTemAgendamento(medico.getId(),StatusAgendamento.AGENDADO, inicio, fim);
        boolean pacienteTemAgendamento = repository.pacienteTemAgendamento(paciente.getId(), StatusAgendamento.AGENDADO, inicio,fim);

        if(medicoTemAgendamento){
            throw new ConflitoDeDadosException("Médico {"+ medico.getNome()+"} já possui uma consulta agendada neste horário");
        }
        if(pacienteTemAgendamento){
            throw new ConflitoDeDadosException("Paciente {"+ paciente.getNome()+"} já possui uma consulta agendada neste horário");
        }
    }
    public void verificarHorarioComercial(AgendamentoRequestDto dto) throws BadRequestException {
        DayOfWeek diaDaSemana = dto.dataAgendamento().getDayOfWeek();
        if(diaDaSemana.equals(DayOfWeek.SUNDAY)){
            throw new ConflitoDeDadosException("Consulta agendada para dia fora do periodo de funcionamento");
        }

        if(dto.dataAgendamento().getHour() < 7 || dto.dataAgendamento().getHour() > 18){
            throw new ConflitoDeDadosException("Consulta agendada para horário fora periodo de funcionamento");
        }

        Duration diferenca = Duration.between(LocalDateTime.now(), dto.dataAgendamento());
        if(dto.dataAgendamento().isBefore(LocalDateTime.now()) || diferenca.toMinutes() < 30){
            throw new ConflitoDeDadosException("Consulta agendada não respeita o intervalo de 30 min de antecedência");
        }

    }
}
