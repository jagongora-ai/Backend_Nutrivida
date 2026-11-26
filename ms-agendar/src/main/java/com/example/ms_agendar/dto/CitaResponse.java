package com.example.ms_agendar.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.ms_agendar.model.EstadoCita;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 

public class CitaResponse {
 
    private Long id;
    private String tipoConsulta;
    private String prestacion;
    private String duracion;
    private Integer precio;
    private Long doctorId;
    private String doctor;
    private String especialidad;
    private String imagen;
    private LocalDate fecha;
    private String modalidad;
    private String paciente;
    private String rut;
    private String correo;
    private EstadoCita estado;
    
    @JsonFormat (pattern = "HH:mm")
    private LocalTime hora;
}

