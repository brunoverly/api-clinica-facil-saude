package br.com.ClinicaFacilSaude.agendamento;

import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoRequestDto;
import br.com.ClinicaFacilSaude.agendamento.dto.AgendamentoResponseDto;
import br.com.ClinicaFacilSaude.medico.MedicoService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    @Autowired
    private AgendamentoService service;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> create(@Valid @RequestBody AgendamentoRequestDto dto) throws BadRequestException {
        return service.create(dto);
    }

    @GetMapping
    public List<AgendamentoResponseDto> findAll(@RequestParam(required = false) Long medico,
                                                @RequestParam(required = false) Long paciente,
                                                @RequestParam(required = false) LocalDateTime inicio,
                                                @RequestParam(required = false) LocalDateTime fim) {
        return service.findAll(medico, paciente, inicio, fim);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PatchMapping("{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDto> cancel(@PathVariable Long id, @RequestBody AgendamentoRequestDto dto) throws BadRequestException {
        return service.cancel(dto, id);
    }

}
