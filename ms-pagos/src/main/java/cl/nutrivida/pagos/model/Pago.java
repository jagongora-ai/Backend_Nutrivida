package cl.nutrivida.pagos.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cita_id", nullable = false, unique = true)
    private Long citaId;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @Column(nullable = false, length = 20)
    private String estado;
    @Column(name = "medio_pago", nullable = false, length = 80)
    private String medioPago;
    @Column(name = "fecha_pago", nullable = false)
    private Instant fechaPago;

    protected Pago() { }

    public Pago(Long citaId, BigDecimal monto, String medioPago) {
        this.citaId = citaId;
        this.monto = monto;
        this.medioPago = medioPago;
        this.estado = "PAGADO";
        this.fechaPago = Instant.now();
    }

    public Long getId() { return id; }
    public Long getCitaId() { return citaId; }
    public BigDecimal getMonto() { return monto; }
    public String getEstado() { return estado; }
    public String getMedioPago() { return medioPago; }
    public Instant getFechaPago() { return fechaPago; }
}
