package cl.nutrivida.pagos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import cl.nutrivida.pagos.model.DatosCita;

public record CitaResponse(
    Long id,
    String tipoConsulta,
    String prestacion,
    String duracion,
    BigDecimal precio,
    Long doctorId,
    String doctor,
    String especialidad,
    String imagen,
    LocalDate fecha,
    String hora,
    String modalidad,
    String paciente,
    String rut,
    String correo,
    String estado
) {
    public static CitaResponse from(DatosCita cita) {
        return new CitaResponse(cita.getId(), cita.getTipoConsulta(), cita.getPrestacion(),
            cita.getDuracion(), cita.getPrecio(), cita.getDoctorId(), cita.getDoctor(),
            cita.getEspecialidad(), cita.getImagen(), cita.getFecha(), cita.getHora(),
            cita.getModalidad(), cita.getPaciente(), cita.getRut(), cita.getCorreo(), cita.getEstado());
    }
}
