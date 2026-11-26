package cl.duocuc.msusuarios.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.duocuc.msusuarios.dto.LoginRequest;
import cl.duocuc.msusuarios.dto.LoginResponse;
import cl.duocuc.msusuarios.dto.RegistroRequest;
import cl.duocuc.msusuarios.dto.UsuarioResponse;
import cl.duocuc.msusuarios.exception.ConflictoException;
import cl.duocuc.msusuarios.exception.CredencialesInvalidasException;
import cl.duocuc.msusuarios.exception.RecursoNoEncontradoException;
import cl.duocuc.msusuarios.model.Rol;
import cl.duocuc.msusuarios.model.Usuario;
import cl.duocuc.msusuarios.repository.RolRepository;
import cl.duocuc.msusuarios.repository.UsuarioRepository;
import cl.duocuc.msusuarios.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private UsuarioResponse usuarioResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getRut(),
                usuario.getEdad(),
                usuario.getCorreo(),
                usuario.getRol().getNombre(),
                usuario.getProfesionalId());
    }

    public UsuarioResponse registrar(RegistroRequest registroRequest) {

        String correo = registroRequest.getCorreo().trim().toLowerCase();
        String rut = registroRequest.getRut().trim();

        if (usuarioRepository.existsByCorreo(correo)) {
            throw new ConflictoException("El correo ya existe");
        }
        if (usuarioRepository.existsByRut(rut)) {
            throw new ConflictoException("El rut ya existe en el sistema");
        }

        // Rol
        Rol buscandoRol = rolRepository.findByNombre("Cliente")
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el rol cliente"));

        // se crea el usuario ->
        Usuario usuario = new Usuario();
        usuario.setNombre(registroRequest.getNombre().trim());
        usuario.setRut(rut);
        usuario.setEdad(registroRequest.getEdad());
        usuario.setCorreo(correo);

        // Se hashea la contraseña
        usuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));
        usuario.setRol(buscandoRol);
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario guardado = usuarioRepository.save(usuario);
        return usuarioResponse(guardado);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        String correo = loginRequest.getCorreo().trim().toLowerCase();
        Optional<Usuario> buscarCorreo = usuarioRepository.findByCorreo(correo);

        if (buscarCorreo.isEmpty()) {
            throw new CredencialesInvalidasException("Credenciales Invalidas");
        }
        Usuario usuario = buscarCorreo.get();

        if (!usuario.getActivo() || !passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Credenciales Invalidas");
        }

        String token = jwtService.generarToken(usuario);

        return new LoginResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getRut(),
                usuario.getEdad(),
                usuario.getCorreo(),
                usuario.getRol().getNombre(),
                usuario.getProfesionalId(),
                token);
    }

    public UsuarioResponse miPerfil(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        return usuarioResponse(usuario);
    }
}
