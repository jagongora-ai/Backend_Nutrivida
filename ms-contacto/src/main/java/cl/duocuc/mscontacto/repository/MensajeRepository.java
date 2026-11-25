package cl.duocuc.mscontacto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duocuc.mscontacto.model.MensajeContacto;

public interface MensajeRepository extends JpaRepository<MensajeContacto, Long> {

    List<MensajeContacto> findAllByOrderByFechaDesc();

    List<MensajeContacto> findByEstadoOrderByFechaDesc(String estado);

    List<MensajeContacto> findByCorreoOrderByFechaDesc(String correo);
}
