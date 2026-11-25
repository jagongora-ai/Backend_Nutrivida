package cl.nutrivida.pagos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.nutrivida.pagos.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    boolean existsByCitaId(Long citaId);
    Optional<Pago> findByCitaId(Long citaId);
}
