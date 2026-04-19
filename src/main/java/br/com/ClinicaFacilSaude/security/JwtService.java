package br.com.ClinicaFacilSaude.security;

import br.com.ClinicaFacilSaude.Usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    @Value("${keys.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("FinanceApp")
                    .withSubject(usuario.getEmail())
                    .withClaim("role", usuario.getRole().name())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("FinanceApp")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            JWT.require(algorithm)
                    .withIssuer("FinanceApp")
                    .build()
                    .verify(token);

            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }



    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}
