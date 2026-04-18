package br.com.ClinicaFacilSaude.entity;

import br.com.ClinicaFacilSaude.enums.Especialidade;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicos")
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private Especialidade especialidade;
    private boolean ativo;


    @PrePersist
    public void onCreate() {
        ativo = true;
    }
}
