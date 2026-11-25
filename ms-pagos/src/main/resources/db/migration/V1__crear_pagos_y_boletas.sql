CREATE TABLE pagos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cita_id BIGINT NOT NULL,
    cliente_id VARCHAR(100) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    tipo_tarjeta VARCHAR(20) NOT NULL,
    marca_tarjeta VARCHAR(30) NOT NULL,
    ultimos_cuatro VARCHAR(4) NOT NULL,
    fecha_pago TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_pago_cita UNIQUE (cita_id)
);

CREATE TABLE boletas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pago_id BIGINT NOT NULL,
    cita_id BIGINT NOT NULL,
    cliente_id VARCHAR(100) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_emision TIMESTAMP(6) NOT NULL,
    datos_cita_id BIGINT,
    datos_cita_fecha DATE,
    datos_cita_hora VARCHAR(255),
    datos_cita_profesional VARCHAR(255),
    datos_cita_servicio VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT uk_boleta_pago UNIQUE (pago_id),
    CONSTRAINT fk_boleta_pago FOREIGN KEY (pago_id) REFERENCES pagos (id),
    INDEX idx_boleta_cita_cliente (cita_id, cliente_id)
);
