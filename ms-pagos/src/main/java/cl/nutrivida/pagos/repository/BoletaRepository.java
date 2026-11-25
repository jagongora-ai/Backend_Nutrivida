package cl.nutrivida.pagos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.nutrivida.pagos.model.Boleta;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    Optional<Boleta> findByCitaId(Long citaId);
}
