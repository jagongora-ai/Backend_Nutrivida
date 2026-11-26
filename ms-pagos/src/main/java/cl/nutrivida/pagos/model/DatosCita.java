package cl.nutrivida.pagos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Embeddable;

@Embeddable
public class DatosCita {
    private Long id;
    private String tipoConsulta;
    private String prestacion;
    private String duracion;
    private BigDecimal precio;
    private Long doctorId;
    private String doctor;
    private String especialidad;
    private String imagen;
    private LocalDate fecha;
    private String hora;
    private String modalidad;
    private String paciente;
    private String rut;
    private String correo;
    private String estado;

    protected DatosCita() { }

    public DatosCita(Long id, String tipoConsulta, String prestacion, String duracion, BigDecimal precio,
                     Long doctorId, String doctor, String especialidad, String imagen, LocalDate fecha,
                     String hora, String modalidad, String paciente, String rut, String correo, String estado) {
        this.id = id;
        this.tipoConsulta = tipoConsulta;
        this.prestacion = prestacion;
        this.duracion = duracion;
        this.precio = precio;
        this.doctorId = doctorId;
        this.doctor = doctor;
        this.especialidad = especialidad;
        this.imagen = imagen;
        this.fecha = fecha;
        this.hora = hora;
        this.modalidad = modalidad;
        this.paciente = paciente;
        this.rut = rut;
        this.correo = correo;
        this.estado = estado;
    }

    public DatosCita conEstado(String estado) {
        return new DatosCita(id, tipoConsulta, prestacion, duracion, precio, doctorId, doctor,
            especialidad, imagen, fecha, hora, modalidad, paciente, rut, correo, estado);
    }

    public Long getId() { return id; }
    public String getTipoConsulta() { return tipoConsulta; }
    public String getPrestacion() { return prestacion; }
    public String getDuracion() { return duracion; }
    public BigDecimal getPrecio() { return precio; }
    public Long getDoctorId() { return doctorId; }
    public String getDoctor() { return doctor; }
    public String getEspecialidad() { return especialidad; }
    public String getImagen() { return imagen; }
    public LocalDate getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getModalidad() { return modalidad; }
    public String getPaciente() { return paciente; }
    public String getRut() { return rut; }
    public String getCorreo() { return correo; }
    public String getEstado() { return estado; }
}
