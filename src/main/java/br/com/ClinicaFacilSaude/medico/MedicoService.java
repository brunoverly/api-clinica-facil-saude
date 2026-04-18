package br.com.ClinicaFacilSaude.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository repository;
}
