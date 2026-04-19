package br.com.ClinicaFacilSaude.Usuario;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(@Email(message = "Campo obrigatório") String email);

    Optional<Usuario> findByEmail(String email);
}
