package br.com.ClinicaFacilSaude.security;

import br.com.ClinicaFacilSaude.Usuario.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        var tokenJwt = getToken(request);
        if(tokenJwt != null
                && SecurityContextHolder.getContext().getAuthentication() == null
                && tokenService.isTokenValid(tokenJwt)) {

            var subject = tokenService.getSubject(tokenJwt);
            var usuario = usuarioRepository.findByEmail(subject);
            if(usuario.isPresent()) {
                var user = usuario.get();
                var authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }



    private String getToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }

}
