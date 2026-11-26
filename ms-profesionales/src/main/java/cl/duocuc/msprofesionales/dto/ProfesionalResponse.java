package cl.duocuc.msprofesionales.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfesionalResponse {

    private Long id;
    private String nombre;
    private String especialidad;
    private String imagen;
    private String ubicacion;
    private String atencion;
    private String experiencia;
    private String sobreMi;
    private List<String> estudios;
    private List<String> areas;

}
