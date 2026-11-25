package cl.nutrivida.pagos.dto;

import java.math.BigDecimal;
import java.time.Instant;
import cl.nutrivida.pagos.model.Boleta;
import cl.nutrivida.pagos.model.Pago;

public record BoletaResponse(
    Long folio,
    Long citaId,
    Instant emitidaEl,
    String codigoAutorizacion,
    String medioPago,
    BigDecimal neto,
    BigDecimal iva,
    BigDecimal total,
    CitaResponse cita
) {
    public static BoletaResponse from(Boleta boleta, Pago pago) {
        return new BoletaResponse(
            boleta.getId(),
            boleta.getCitaId(),
            boleta.getFechaEmision(),
            boleta.getCodigoAutorizacion(),
            pago.getMedioPago(),
            boleta.getNeto(),
            boleta.getIva(),
            boleta.getMonto(),
            CitaResponse.from(boleta.getCita())
        );
    }
}
