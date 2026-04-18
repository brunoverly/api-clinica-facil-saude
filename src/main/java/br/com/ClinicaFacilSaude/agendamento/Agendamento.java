package br.com.ClinicaFacilSaude.agendamento;

import br.com.ClinicaFacilSaude.medico.Medico;
import br.com.ClinicaFacilSaude.paciente.Paciente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agendamentos")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Medico medico;
    @ManyToOne(fetch = FetchType.LAZY)
    Paciente paciente;
    LocalDateTime dataAgendamento;
    StatusAgendamento status;
    String motivoCancelamento;
}
