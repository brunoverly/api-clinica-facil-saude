package br.com.ClinicaFacilSaude.Auth;


import br.com.ClinicaFacilSaude.Auth.dto.AuthRequestDto;
import br.com.ClinicaFacilSaude.Auth.dto.AuthResponseDto;
import br.com.ClinicaFacilSaude.Usuario.Usuario;
import br.com.ClinicaFacilSaude.Usuario.UsuarioMapper;
import br.com.ClinicaFacilSaude.Usuario.UsuarioRepository;
import br.com.ClinicaFacilSaude.Usuario.dto.UsuarioRequestDto;
import br.com.ClinicaFacilSaude.exception.ConflitoDeDadosException;
import br.com.ClinicaFacilSaude.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioMapper mapper;

    @Autowired
    private JwtService jwtService;


    public AuthResponseDto create(UsuarioRequestDto dto) throws BadRequestException {
        if(!dto.senha().equals(dto.senha())) {
            throw new BadRequestException("Senhas informadas não conferem");
        }
        if(repository.existsByEmail(dto.email())) {
            throw new ConflitoDeDadosException("Email {"+ dto.email() +"} já em uso");
        }

        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        repository.save(usuario);

        AuthResponseDto auth = new  AuthResponseDto(usuario.getNome(), jwtService.generateToken(usuario));
        return auth;
    }

    public AuthResponseDto login(AuthRequestDto dto) throws Exception {
        Usuario usuario = repository.findByEmail(dto.email()).orElseThrow(
                () ->  new EntityNotFoundException("Email não localizado no banco de dados"));

        if(!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new BadRequestException("Senha incorreta!");
        }

        AuthResponseDto auth = new  AuthResponseDto(usuario.getNome(), jwtService.generateToken(usuario));
        return auth;


    }
}

