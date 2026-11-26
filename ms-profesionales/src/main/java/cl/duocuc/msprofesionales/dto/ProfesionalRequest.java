package cl.duocuc.msprofesionales.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesionalRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 7, max = 50, message = "El nombre debe tener entre 7 a 50 caracteres")
    @Pattern(regexp = "^[^0-9]*$", message = "El nombre no debe contener numeros")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatorio")
    @Size(max = 120, message = "El maximo de caracteres es de 120")
    private String especialidad;

    @NotBlank(message = "La imagen es obligatoria")
    @Size(max = 255, message = "La ruta de la imagen no debe superar los 255 caracteres")
    @Pattern(regexp = "(?i)^.*\\.(jpg|jpeg|png|gif|webp)$", message = "La imagen debe tener un formato válido (.jpg, .png, .webp)")
    private String imagen;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 80, message = "La ubicación no debe superar los 80 caracteres")
    private String ubicacion;

    @NotBlank(message = "El tipo de atención es obligatorio")
    @Size(max = 40, message = "El tipo de atención no debe superar los 40 caracteres")
    private String atencion;

    @NotBlank(message = "La experiencia es obligatoria")
    @Size(max = 120, message = "La experiencia no debe superar los 120 caracteres")
    private String experiencia;

    @Size(max = 1000, message = "El texto sobre mi no debe superar los 1000 caracteres")
    private String sobreMi;

    @NotEmpty(message = "Debe ingresar al menos un estudio")
    private List<String> estudios;

    @NotEmpty(message = "Debe ingresar al menos un área de atención")
    private List<String> areas;
}
