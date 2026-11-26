package com.example.ms_agendar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@JsonIgnoreProperties (ignoreUnknown = true)
public class ProfesionalExterno {
    private Long id;
    private String nombre;
    private String especialidad;
    private String imagen;
}
