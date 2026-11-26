package com.example.ms_agendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class PrestacionResponse {
 
    private Long id;
 
    private String valor;
 
    private String etiqueta;
 
    private Integer precio;
 
    private String duracion;
}
