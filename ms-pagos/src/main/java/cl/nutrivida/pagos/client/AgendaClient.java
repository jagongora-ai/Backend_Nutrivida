package cl.nutrivida.pagos.client;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import cl.nutrivida.pagos.exception.AgendaException;
import cl.nutrivida.pagos.model.DatosCita;
import cl.nutrivida.pagos.dto.CitaResponse;

@Component
public class AgendaClient {
    private final WebClient webClient;
    private final String claveInterna;

    public AgendaClient(WebClient.Builder builder,
                        @Value("${agenda.service-id:MS-AGENDA}") String serviceId,
                        @Value("${servicios.clave-interna}") String claveInterna) {
        this.webClient = builder.baseUrl("http://" + serviceId).build();
        this.claveInterna = claveInterna;
    }

    // se reenvia el token del cliente porque ms-agendar revisa que la cita sea suya
    public CitaAgenda obtenerCita(Long citaId, String token) {
        try {
            CitaResponse cita = webClient.get()
                .uri("/api/citas/{id}", citaId)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(CitaResponse.class)
                .block();
            if (cita == null || !citaId.equals(cita.id()) || cita.precio() == null
                || cita.precio().signum() <= 0) {
                throw new AgendaException(503, "ms-agenda no entregó un precio de cita válido.");
            }
            DatosCita datos = new DatosCita(cita.id(), cita.tipoConsulta(), cita.prestacion(),
                cita.duracion(), cita.precio(), cita.doctorId(), cita.doctor(), cita.especialidad(),
                cita.imagen(), cita.fecha(), cita.hora(), cita.modalidad(), cita.paciente(),
                cita.rut(), cita.correo(), cita.estado());
            return new CitaAgenda(cita.precio(), datos);
        } catch (AgendaException exception) {
            throw exception;
        } catch (WebClientResponseException exception) {
            throw traducir(exception);
        } catch (WebClientRequestException exception) {
            throw new AgendaException(503, "ms-agenda no está disponible.");
        }
    }

    // marcar pagada no usa el token del cliente: va con la clave interna que solo conocen los micros
    public void marcarPagada(Long citaId) {
        try {
            webClient.patch()
                .uri("/api/citas/{id}/pagada", citaId)
                .header("X-ClaveInterna", claveInterna)
                .retrieve()
                .toBodilessEntity()
                .block();
        } catch (WebClientResponseException exception) {
            throw traducir(exception);
        } catch (WebClientRequestException exception) {
            throw new AgendaException(503, "No fue posible actualizar la cita en ms-agenda.");
        }
    }

    private AgendaException traducir(WebClientResponseException exception) {
        if (exception.getStatusCode().value() == 404) {
            return new AgendaException(404, "La cita solicitada no existe.");
        }
        return new AgendaException(503, "ms-agenda no está disponible o no pudo procesar la solicitud.");
    }

    public record CitaAgenda(BigDecimal precio, DatosCita datosCita) { }
}
