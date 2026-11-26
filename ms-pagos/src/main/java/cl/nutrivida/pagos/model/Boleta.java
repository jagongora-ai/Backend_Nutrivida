package cl.nutrivida.pagos.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.Embedded;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "boletas", indexes = @Index(name = "idx_boleta_cita", columnList = "cita_id"))
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pago_id", nullable = false, unique = true)
    private Long pagoId;
    @Column(name = "cita_id", nullable = false)
    private Long citaId;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal neto;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;
    @Column(name = "codigo_autorizacion", nullable = false, length = 6)
    private String codigoAutorizacion;
    @Column(name = "fecha_emision", nullable = false)
    private Instant fechaEmision;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "datos_cita_id")),
        @AttributeOverride(name = "tipoConsulta", column = @Column(name = "datos_cita_tipo_consulta", length = 100)),
        @AttributeOverride(name = "prestacion", column = @Column(name = "datos_cita_servicio", length = 255)),
        @AttributeOverride(name = "duracion", column = @Column(name = "datos_cita_duracion", length = 50)),
        @AttributeOverride(name = "precio", column = @Column(name = "datos_cita_precio", precision = 10, scale = 2)),
        @AttributeOverride(name = "doctorId", column = @Column(name = "datos_cita_doctor_id")),
        @AttributeOverride(name = "doctor", column = @Column(name = "datos_cita_profesional", length = 255)),
        @AttributeOverride(name = "especialidad", column = @Column(name = "datos_cita_especialidad", length = 150)),
        @AttributeOverride(name = "imagen", column = @Column(name = "datos_cita_imagen", length = 500)),
        @AttributeOverride(name = "fecha", column = @Column(name = "datos_cita_fecha")),
        @AttributeOverride(name = "hora", column = @Column(name = "datos_cita_hora")),
        @AttributeOverride(name = "modalidad", column = @Column(name = "datos_cita_modalidad", length = 50)),
        @AttributeOverride(name = "paciente", column = @Column(name = "datos_cita_paciente", length = 120)),
        @AttributeOverride(name = "rut", column = @Column(name = "datos_cita_rut", length = 12)),
        @AttributeOverride(name = "correo", column = @Column(name = "datos_cita_correo", length = 100)),
        @AttributeOverride(name = "estado", column = @Column(name = "datos_cita_estado", length = 30))
    })
    private DatosCita cita;

    protected Boleta() { }

    public Boleta(Long pagoId, Long citaId, BigDecimal monto, BigDecimal neto,
                  BigDecimal iva, String codigoAutorizacion, DatosCita cita) {
        this.pagoId = pagoId;
        this.citaId = citaId;
        this.monto = monto;
        this.neto = neto;
        this.iva = iva;
        this.codigoAutorizacion = codigoAutorizacion;
        this.fechaEmision = Instant.now();
        this.cita = cita;
    }

    public Long getId() { return id; }
    public Long getPagoId() { return pagoId; }
    public Long getCitaId() { return citaId; }
    public BigDecimal getMonto() { return monto; }
    public BigDecimal getNeto() { return neto; }
    public BigDecimal getIva() { return iva; }
    public String getCodigoAutorizacion() { return codigoAutorizacion; }
    public Instant getFechaEmision() { return fechaEmision; }
    public DatosCita getCita() { return cita; }
}
