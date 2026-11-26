package cl.duocuc.msprofesionales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duocuc.msprofesionales.model.Profesional;

public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    List<Profesional> findAllByOrderByNombreAsc();

    List<Profesional> findByNombreContainingIgnoreCaseOrEspecialidadContainingIgnoreCaseOrderByNombreAsc(String nombre, String especialidad);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
