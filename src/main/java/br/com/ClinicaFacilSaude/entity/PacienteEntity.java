package br.com.ClinicaFacilSaude.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @UniqueElements
    private String email;
    private String cpf;
    private String telefone;
    private boolean ativo;


    @PrePersist
    public void onCreate() {
        this.ativo = true;
    }
}
