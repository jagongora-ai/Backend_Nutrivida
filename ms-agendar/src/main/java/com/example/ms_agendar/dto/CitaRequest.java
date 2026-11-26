package com.example.ms_agendar.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class CitaRequest {

    @NotBlank(message = "Por favor selecciona el tipo de consulta / prestación.")
    private String tipoConsulta;
 
    @NotNull(message = "Por favor selecciona un doctor o profesional.")
    private Long doctorId;
 
    @NotNull(message = "La fecha de la cita es obligatoria.")
    private LocalDate fecha;
 
    @NotNull(message = "La hora de la cita es obligatoria.")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;
 
    @NotNull(message = "Por favor selecciona la modalidad.")
    @Pattern (regexp = "^(Presencial|Online)$", message = "La modalidad debe ser Presencial u Online.")
    private String modalidad;
}