package cl.duocuc.msusuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duocuc.msusuarios.model.Usuario;
import java.util.Optional;


public interface UsuarioRepository extends  JpaRepository<Usuario, Long>{

    Optional<Usuario> findByCorreo(String correo);
    Boolean existsByRut(String rut);
    Boolean existsByCorreo(String correo);

}
