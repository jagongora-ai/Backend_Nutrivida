package cl.duocuc.mscontacto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensajeRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 a 50 caracteres")
    @Pattern(regexp = "^[^0-9]*$", message = "El nombre no debe contener números")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingrese un correo con formato válido")
    @Size(max = 100, message = "El correo no debe superar los 100 caracteres")
    private String correo;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 80, message = "El asunto no debe superar los 80 caracteres")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, max = 500, message = "El mensaje debe tener entre 10 a 500 caracteres")
    private String mensaje;
}
