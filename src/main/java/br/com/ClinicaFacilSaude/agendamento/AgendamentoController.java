package br.com.ClinicaFacilSaude.agendamento;

import br.com.ClinicaFacilSaude.medico.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    @Autowired
    private MedicoService service;
}
