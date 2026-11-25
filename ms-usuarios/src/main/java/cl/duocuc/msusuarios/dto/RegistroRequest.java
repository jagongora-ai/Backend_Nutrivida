package cl.duocuc.msusuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 7, max = 50, message = "El nombre debe tener entre 7 a 50 caracteres")
    @Pattern(regexp = "^[^0-9]*$", message = "El nombre no debe contener numeros")
    private String nombre;

    @NotBlank(message = "El Rut es obligatorio")
    @Size(max = 10, message = "El maximo de caracteres para rut es de 10 caracteres")
    @Pattern(regexp = "^\\S+$", message = "El RUT no debe contener espacios en blanco")
    private String rut;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad maxima es de 120 años y la edad minima de registro es de 18 años")
    @Max(value = 120, message = "La edad maxima es de 120 años y la edad minima de registro es de 18 años")
    private Integer edad;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 12, message = "La contraseña debe tener entre 8 a 12 caracteres")
    private String password;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingrese un correo con formato valido")
    @Size(min = 7, max = 100, message = "El correo debe tener entre 7 a 100 caracteres")
    private String correo;
}
