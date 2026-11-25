package cl.nutrivida.fichas.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "fichas_paciente",
    uniqueConstraints = @UniqueConstraint(name = "uk_ficha_rut", columnNames = "rut"))
public class FichaPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String nombre;
    @Column(nullable = false, length = 12)
    private String rut;
    @Column(nullable = false)
    private Integer edad;
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    @Column(nullable = false, length = 9)
    private String telefono;
    @Column(nullable = false, length = 100)
    private String correo;
    @Column(nullable = false, length = 500)
    private String enfermedades;
    @Column(nullable = false, length = 500)
    private String alergias;
    @Column(nullable = false, length = 500)
    private String medicamentos;
    @Column(nullable = false, length = 15)
    private String alimentacion;
    @Column(nullable = false)
    private Integer comidas;
    @Column(nullable = false)
    private Integer agua;
    @Column(nullable = false, length = 15)
    private String actividad;
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal peso;
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal estatura;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal imc;
    @Column(nullable = false, length = 300)
    private String objetivo;
    @Column(nullable = false, length = 1000)
    private String observaciones;
    @Column(nullable = false, length = 1000)
    private String recomendaciones;
    @Column(name = "id_medico", nullable = false)
    private Long idMedico;

    public FichaPaciente() { }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getEnfermedades() { return enfermedades; }
    public void setEnfermedades(String enfermedades) { this.enfermedades = enfermedades; }
    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
    public String getAlimentacion() { return alimentacion; }
    public void setAlimentacion(String alimentacion) { this.alimentacion = alimentacion; }
    public Integer getComidas() { return comidas; }
    public void setComidas(Integer comidas) { this.comidas = comidas; }
    public Integer getAgua() { return agua; }
    public void setAgua(Integer agua) { this.agua = agua; }
    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }
    public BigDecimal getPeso() { return peso; }
    public void setPeso(BigDecimal peso) { this.peso = peso; }
    public BigDecimal getEstatura() { return estatura; }
    public void setEstatura(BigDecimal estatura) { this.estatura = estatura; }
    public BigDecimal getImc() { return imc; }
    public void setImc(BigDecimal imc) { this.imc = imc; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }
    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }
}
