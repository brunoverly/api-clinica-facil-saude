package br.com.ClinicaFacilSaude.paciente;

import br.com.ClinicaFacilSaude.paciente.dto.PacienteRequestDto;
import br.com.ClinicaFacilSaude.paciente.dto.PacienteResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService service;


    @PostMapping
    public ResponseEntity<PacienteResponseDto> create(@Valid @RequestBody PacienteRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public Page<PacienteResponseDto> findAll(@PageableDefault Pageable pageable) {
        return service.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> update(@PathVariable Long id, @Valid @RequestBody PacienteRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
