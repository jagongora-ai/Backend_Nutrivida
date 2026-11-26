package cl.duocuc.msusuarios.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.duocuc.msusuarios.model.Rol;
import cl.duocuc.msusuarios.model.Usuario;
import cl.duocuc.msusuarios.repository.RolRepository;
import cl.duocuc.msusuarios.repository.UsuarioRepository;

@Configuration
public class CargaDatosConfig {

    @Bean
    CommandLineRunner initUsuarios(RolRepository rolRepository, UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol(1L, "Cliente"));
                rolRepository.save(new Rol(2L, "medico"));
                rolRepository.save(new Rol(3L, "admin"));
            }

            if (usuarioRepository.count() == 0) {
                Rol admin = rolRepository.findById(3L).orElseThrow();
                Rol medico = rolRepository.findById(2L).orElseThrow();

                usuarioRepository.save(new Usuario(null, "Joaquin Andres vargas", "2234567-1", 35,
                        "admin@nutrivida.cl", passwordEncoder.encode("admin2026Nutri"),
                        null, true, LocalDateTime.now(), admin));

                usuarioRepository.save(new Usuario(null, "Carolina fuentes", "12345453-1", 23,
                        "medico@nutrivida.cl", passwordEncoder.encode("medico2026Nutri"),
                        1L, true, LocalDateTime.now(), medico));
            }
        };
    }
}
