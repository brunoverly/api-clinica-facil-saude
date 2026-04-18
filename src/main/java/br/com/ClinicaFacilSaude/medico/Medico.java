package br.com.ClinicaFacilSaude.medico;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicos")
public class Medico {
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
