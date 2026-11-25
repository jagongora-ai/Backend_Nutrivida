package cl.duocuc.mscontacto.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeResponse {

    private Long id;
    private String nombre;
    private String correo;
    private String asunto;
    private String mensaje;
    private LocalDateTime fecha;
    private String estado;
    private String respuesta;
    private LocalDateTime fechaRespuesta;
}
