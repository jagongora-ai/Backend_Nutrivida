package cl.duocuc.mscontacto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaRequest {

    @NotBlank(message = "La respuesta es obligatoria")
    @Size(min = 5, max = 500, message = "La respuesta debe tener entre 5 a 500 caracteres")
    private String respuesta;
}
