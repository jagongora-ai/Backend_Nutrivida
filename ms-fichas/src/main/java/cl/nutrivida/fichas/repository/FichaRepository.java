package cl.nutrivida.fichas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.nutrivida.fichas.model.FichaPaciente;

public interface FichaRepository extends JpaRepository<FichaPaciente, Long> {
    List<FichaPaciente> findAllByOrderByNombreAsc();
    List<FichaPaciente> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    boolean existsByRutIgnoreCase(String rut);
    boolean existsByRutIgnoreCaseAndIdNot(String rut, Long id);
}
