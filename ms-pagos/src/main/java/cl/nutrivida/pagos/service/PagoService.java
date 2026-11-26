package cl.nutrivida.pagos.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.nutrivida.pagos.client.AgendaClient;
import cl.nutrivida.pagos.dto.BoletaResponse;
import cl.nutrivida.pagos.dto.PagoRequest;
import cl.nutrivida.pagos.exception.BoletaNoEncontradaException;
import cl.nutrivida.pagos.exception.PagoDuplicadoException;
import cl.nutrivida.pagos.model.Boleta;
import cl.nutrivida.pagos.model.Pago;
import cl.nutrivida.pagos.repository.BoletaRepository;
import cl.nutrivida.pagos.repository.PagoRepository;

@Service
@Transactional
public class PagoService {
    private static final SecureRandom RANDOM = new SecureRandom();
    private final PagoRepository pagoRepository;
    private final BoletaRepository boletaRepository;
    private final AgendaClient agendaClient;

    public PagoService(PagoRepository pagoRepository, BoletaRepository boletaRepository, AgendaClient agendaClient) {
        this.pagoRepository = pagoRepository;
        this.boletaRepository = boletaRepository;
        this.agendaClient = agendaClient;
    }

    public BoletaResponse pagar(PagoRequest request, String token) {
        if (pagoRepository.existsByCitaId(request.citaId())) throw new PagoDuplicadoException();
        AgendaClient.CitaAgenda cita = agendaClient.obtenerCita(request.citaId(), token);

        BigDecimal neto = cita.precio().divide(new BigDecimal("1.19"), 0, RoundingMode.HALF_UP);
        BigDecimal iva = cita.precio().subtract(neto);
        Pago pago = pagoRepository.saveAndFlush(new Pago(request.citaId(), cita.precio(), request.medioPago()));
        Boleta boleta = boletaRepository.save(new Boleta(pago.getId(), request.citaId(),
            cita.precio(), neto, iva, codigoAutorizacion(),
            cita.datosCita().conEstado("Pagada")));
        agendaClient.marcarPagada(request.citaId());
        return BoletaResponse.from(boleta, pago);
    }

    @Transactional(readOnly = true)
    public BoletaResponse obtenerBoleta(Long citaId) {
        Boleta boleta = boletaRepository.findByCitaId(citaId)
            .orElseThrow(BoletaNoEncontradaException::new);
        Pago pago = pagoRepository.findByCitaId(citaId)
            .orElseThrow(BoletaNoEncontradaException::new);
        return BoletaResponse.from(boleta, pago);
    }

    private static String codigoAutorizacion() {
        return "%06d".formatted(RANDOM.nextInt(1_000_000));
    }
}
