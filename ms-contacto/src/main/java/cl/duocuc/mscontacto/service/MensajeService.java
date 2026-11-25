package cl.duocuc.mscontacto.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import cl.duocuc.mscontacto.dto.MensajeRequest;
import cl.duocuc.mscontacto.dto.MensajeResponse;
import cl.duocuc.mscontacto.dto.RespuestaRequest;
import cl.duocuc.mscontacto.exception.ConflictoException;
import cl.duocuc.mscontacto.exception.RecursoNoEncontradoException;
import cl.duocuc.mscontacto.model.MensajeContacto;
import cl.duocuc.mscontacto.repository.MensajeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MensajeService {

    private final MensajeRepository repository;

    private MensajeResponse response(MensajeContacto mensajeContacto) {
        return new MensajeResponse(
                mensajeContacto.getId(),
                mensajeContacto.getNombre(),
                mensajeContacto.getCorreo(),
                mensajeContacto.getAsunto(),
                mensajeContacto.getMensaje(),
                mensajeContacto.getFecha(),
                mensajeContacto.getEstado(),
                mensajeContacto.getRespuesta(),
                mensajeContacto.getFechaRespuesta());
    }

    public MensajeResponse enviar(MensajeRequest mensajeRequest, Long idUsuario) {

        MensajeContacto mensajeContacto = new MensajeContacto();
        mensajeContacto.setIdUsuario(idUsuario);
        mensajeContacto.setAsunto(mensajeRequest.getAsunto().trim());
        mensajeContacto.setNombre(mensajeRequest.getNombre().trim());
        mensajeContacto.setCorreo(mensajeRequest.getCorreo().trim().toLowerCase());
        mensajeContacto.setMensaje(mensajeRequest.getMensaje().trim());
        mensajeContacto.setFecha(LocalDateTime.now());
        mensajeContacto.setEstado("Pendiente");
        mensajeContacto.setRespuesta("");

        MensajeContacto guardado = repository.save(mensajeContacto);
        return response(guardado);
    }

    public List<MensajeResponse> misMensajes(String correo) {

        List<MensajeContacto> mensajes = repository.findByCorreoOrderByFechaDesc(correo.toLowerCase());
        List<MensajeResponse> resultado = new ArrayList<>();

        for (MensajeContacto mensajeContacto : mensajes) {
            resultado.add(response(mensajeContacto));
        }
        return resultado;
    }

    public List<MensajeResponse> listarMensajes(String estado) {
        List<MensajeContacto> mensajes;
        List<MensajeResponse> resultado = new ArrayList<>();
        if (estado == null || estado.isBlank()) {
            mensajes = repository.findAllByOrderByFechaDesc();
        } else {
            mensajes = repository.findByEstadoOrderByFechaDesc(estado);
        }

        for (MensajeContacto mensajeContacto : mensajes) {
            resultado.add(response(mensajeContacto));
        }
        return resultado;
    }

    public MensajeResponse responder(Long id, RespuestaRequest request) {
        MensajeContacto mensaje = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El mensaje ya no existe"));

        if ("Respondido".equals(mensaje.getEstado())) {
            throw new ConflictoException("El mensaje ya fue respondido");
        }

        mensaje.setRespuesta(request.getRespuesta().trim());
        mensaje.setFechaRespuesta(LocalDateTime.now());
        mensaje.setEstado("Respondido");

        MensajeContacto guardado = repository.save(mensaje);

        return response(guardado);
    }
}
