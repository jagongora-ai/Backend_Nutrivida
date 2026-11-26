package cl.nutrivida.fichas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import cl.nutrivida.fichas.model.FichaPaciente;

public record FichaResponse(
    Long id, String nombre, String rut, Integer edad, LocalDate fechaNacimiento,
    String telefono, String correo, String enfermedades, String alergias, String medicamentos,
    String alimentacion, Integer comidas, Integer agua, String actividad,
    BigDecimal peso, BigDecimal estatura, BigDecimal imc, String objetivo,
    String observaciones, String recomendaciones
) {
    public static FichaResponse from(FichaPaciente ficha) {
        return new FichaResponse(ficha.getId(), ficha.getNombre(), ficha.getRut(), ficha.getEdad(),
            ficha.getFechaNacimiento(), ficha.getTelefono(), ficha.getCorreo(), ficha.getEnfermedades(),
            ficha.getAlergias(), ficha.getMedicamentos(), ficha.getAlimentacion(), ficha.getComidas(),
            ficha.getAgua(), ficha.getActividad(), ficha.getPeso(), ficha.getEstatura(), ficha.getImc(),
            ficha.getObjetivo(), ficha.getObservaciones(), ficha.getRecomendaciones());
    }
}
