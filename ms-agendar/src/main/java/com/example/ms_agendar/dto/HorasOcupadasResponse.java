package com.example.ms_agendar.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class HorasOcupadasResponse {
 
    private Long doctorId;
    private LocalDate fecha;
 
    private List<String> horasOcupadas;
}
