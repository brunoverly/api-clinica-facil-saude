package br.com.ClinicaFacilSaude.medico;

import br.com.ClinicaFacilSaude.medico.dto.MedicoRequestDto;
import br.com.ClinicaFacilSaude.medico.dto.MedicoResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoService service;

    @PostMapping
    public ResponseEntity<MedicoResponseDto> create(@Valid @RequestBody MedicoRequestDto dto){
        return service.create(dto);
    }

    @GetMapping
    public Page<MedicoResponseDto> findAll(@PageableDefault Pageable pageable){
        return service.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDto> update(@Valid @RequestBody MedicoRequestDto dto, @PathVariable Long id){
        return service.update(id ,dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return service.delete(id);
    }

}
