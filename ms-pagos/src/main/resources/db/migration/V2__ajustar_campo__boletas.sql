ALTER TABLE boletas
    ADD COLUMN neto DECIMAL(10,2) NULL,
    ADD COLUMN iva DECIMAL(10,2) NULL,
    ADD COLUMN codigo_autorizacion VARCHAR(6) NULL,
    ADD COLUMN datos_cita_tipo_consulta VARCHAR(100) NULL,
    ADD COLUMN datos_cita_duracion VARCHAR(50) NULL,
    ADD COLUMN datos_cita_precio DECIMAL(10,2) NULL,
    ADD COLUMN datos_cita_doctor_id BIGINT NULL,
    ADD COLUMN datos_cita_especialidad VARCHAR(150) NULL,
    ADD COLUMN datos_cita_imagen VARCHAR(500) NULL,
    ADD COLUMN datos_cita_modalidad VARCHAR(50) NULL,
    ADD COLUMN datos_cita_paciente VARCHAR(120) NULL,
    ADD COLUMN datos_cita_rut VARCHAR(12) NULL,
    ADD COLUMN datos_cita_correo VARCHAR(100) NULL,
    ADD COLUMN datos_cita_estado VARCHAR(30) NULL;

UPDATE boletas
SET neto = ROUND(monto / 1.19, 0),
    iva = monto - ROUND(monto / 1.19, 0),
    codigo_autorizacion = LPAD(id, 6, '0');

ALTER TABLE boletas
    MODIFY COLUMN neto DECIMAL(10,2) NOT NULL,
    MODIFY COLUMN iva DECIMAL(10,2) NOT NULL,
    MODIFY COLUMN codigo_autorizacion VARCHAR(6) NOT NULL;
