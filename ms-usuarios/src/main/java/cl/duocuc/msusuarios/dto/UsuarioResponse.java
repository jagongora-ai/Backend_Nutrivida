package cl.duocuc.msusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String rut;
    private Integer edad;
    private String correo;
    private String rol;
    private Long profesionalId;

}
