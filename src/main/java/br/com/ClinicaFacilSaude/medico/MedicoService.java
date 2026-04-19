package br.com.ClinicaFacilSaude.medico;

import br.com.ClinicaFacilSaude.exception.ConflitoDeDadosException;
import br.com.ClinicaFacilSaude.medico.dto.MedicoRequestDto;
import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
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
public class MedicoService {
    @Autowired
    private MedicoRepository repository;
    @Autowired
    private MedicoMapper mapper;

    public ResponseEntity<MedicoResponseDto> create(@Valid MedicoRequestDto dto){
        if (repository.existsByEmailOrCrm(dto.email(), dto.crm())) {
            throw new ConflitoDeDadosException("Email ou CRM informados já cadastrados");
        }

        Medico medico = repository.save(mapper.toEntity(dto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medico.getId())
                .toUri();
        
        return ResponseEntity.created(uri).body(mapper.toResponse(medico));
    }

    public Page<MedicoResponseDto> findAll(Pageable pageable) {
        Page <Medico> medico = repository.findAllByAtivoTrue(pageable);
        return medico.map(mapper::toResponse);
    }

    public ResponseEntity<MedicoResponseDto> update(Long id, @Valid MedicoRequestDto dto) {
        Medico medico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico com id {"+ id +"} não localizado"));

        medico.setCrm(dto.crm());
        medico.setNome(dto.nome());
        medico.setEmail(dto.email());
        medico.setEspecialidade(Especialidade.valueOf(dto.especialidade()));

        repository.save(medico);

        return ResponseEntity.ok(mapper.toResponse(medico));


    }

    public ResponseEntity delete(Long id) {
        Medico medico = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico com id {"+ id +"} não localizado"));

        medico.setAtivo(false);
        repository.save(medico);

        return ResponseEntity.noContent().build();
    }
}
