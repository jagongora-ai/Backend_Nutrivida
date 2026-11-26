package cl.nutrivida.fichas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FichaRequest(
    @NotBlank(message = "Ingresa el nombre del paciente.")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres.")
    String nombre,
    @NotBlank(message = "Ingresa el RUT del paciente.")
    @Pattern(regexp = "^\\d{7,8}-[\\dkK]$",
        message = "Ingresa un RUT válido, por ejemplo 12345678-9.")
    @Size(max = 12, message = "El RUT no puede superar 12 caracteres.")
    String rut,
    @NotNull(message = "Ingresa la edad del paciente.")
    @Min(value = 1, message = "Ingresa una edad válida entre 1 y 120 años.")
    @Max(value = 120, message = "Ingresa una edad válida entre 1 y 120 años.")
    Integer edad,
    @NotNull(message = "Selecciona la fecha de nacimiento del paciente.")
    @PastOrPresent(message = "Ingresa una fecha de nacimiento válida.")
    LocalDate fechaNacimiento,
    @NotBlank(message = "Ingresa el teléfono del paciente.")
    @Pattern(regexp = "^\\d{9}$",
        message = "El teléfono debe contener exactamente 9 dígitos seguidos.")
    String telefono,
    @NotBlank(message = "Ingresa el correo electrónico del paciente.")
    @Email(message = "Ingresa un correo electrónico válido.")
    @Size(max = 100, message = "El correo no puede superar 100 caracteres.")
    String correo,
    @NotBlank(message = "Ingresa las enfermedades del paciente o escribe \"Ninguna\".")
    @Size(max = 500, message = "Enfermedades no puede superar 500 caracteres.")
    String enfermedades,
    @NotBlank(message = "Ingresa las alergias del paciente o escribe \"Ninguna\".")
    @Size(max = 500, message = "Alergias no puede superar 500 caracteres.")
    String alergias,
    @NotBlank(message = "Ingresa los medicamentos del paciente o escribe \"Ninguno\".")
    @Size(max = 500, message = "Medicamentos no puede superar 500 caracteres.")
    String medicamentos,
    @NotBlank(message = "Selecciona el tipo de alimentación.")
    @Pattern(regexp = "^(Omnívora|Vegetariana|Vegana|Otra)$",
        message = "Selecciona un tipo de alimentación válido.")
    @Size(max = 15, message = "El tipo de alimentación no puede superar 15 caracteres.")
    String alimentacion,
    @NotNull(message = "Ingresa la cantidad de comidas al día.")
    @Min(value = 1, message = "Ingresa una cantidad válida de comidas al día.")
    @Max(value = 15, message = "Ingresa una cantidad válida de comidas al día.")
    Integer comidas,
    @NotNull(message = "Ingresa la cantidad de vasos de agua al día.")
    @Min(value = 0, message = "Ingresa una cantidad válida de vasos de agua, entre 0 y 40.")
    @Max(value = 40, message = "Ingresa una cantidad válida de vasos de agua, entre 0 y 40.")
    Integer agua,
    @NotBlank(message = "Selecciona el nivel de actividad física.")
    @Pattern(regexp = "^(Sedentaria|Leve|Moderada|Intensa)$",
        message = "Selecciona un nivel de actividad física válido.")
    @Size(max = 15, message = "La actividad no puede superar 15 caracteres.")
    String actividad,
    @NotNull(message = "Ingresa un peso válido.")
    @Positive(message = "Ingresa un peso válido.")
    @DecimalMax(value = "800", message = "Ingresa un peso válido.")
    BigDecimal peso,
    @NotNull(message = "Ingresa una estatura válida.")
    @Positive(message = "Ingresa una estatura válida.")
    @DecimalMax(value = "3", message = "Ingresa una estatura válida.")
    BigDecimal estatura,
    @NotBlank(message = "Ingresa el objetivo nutricional.")
    @Size(max = 300, message = "El objetivo no puede superar 300 caracteres.")
    String objetivo,
    @NotBlank(message = "Ingresa las observaciones del profesional.")
    @Size(max = 1000, message = "Las observaciones no pueden superar 1000 caracteres.")
    String observaciones,
    @NotBlank(message = "Ingresa las recomendaciones.")
    @Size(max = 1000, message = "Las recomendaciones no pueden superar 1000 caracteres.")
    String recomendaciones
) { }
