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
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    private boolean ativo;


    @PrePersist
    public void onCreate() {
        ativo = true;
    }
}
