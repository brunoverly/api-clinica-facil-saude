package br.com.ClinicaFacilSaude.paciente;

import br.com.ClinicaFacilSaude.exception.ConflitoDeDadosException;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteRequestDto;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repository;
    @Autowired
    private PacienteMapper mapper;

    public ResponseEntity<PacienteResponseDto> create(@Valid PacienteRequestDto dto) {
        if (repository.existsByEmailOrCpf(dto.email(), dto.cpf())) {
            throw new ConflitoDeDadosException("Email ou CPF informados já cadastrados");
        }
        Paciente paciente = repository.save(mapper.toEntity(dto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paciente.getId())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.toResponse(paciente));
    }

    public Page<PacienteResponseDto> findAll(Pageable pageable) {
        Page <Paciente> pacientes = repository.findAllByAtivoTrue(pageable);
        return pacientes.map(mapper::toResponse);
    }

    public ResponseEntity<PacienteResponseDto> update(Long id, @Valid PacienteRequestDto dto) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Paciente com id{"+ id +"} não localizado"));

        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());
        paciente.setTelefone(dto.telefone());

        repository.save(paciente);

        return ResponseEntity.ok(mapper.toResponse(paciente));
    }

    public ResponseEntity delete(Long id) {
        Paciente paciente = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com id{"+ id +"} não localizado"));

        paciente.setAtivo(false);
        repository.save(paciente);
        return ResponseEntity.noContent().build();
    }
}
