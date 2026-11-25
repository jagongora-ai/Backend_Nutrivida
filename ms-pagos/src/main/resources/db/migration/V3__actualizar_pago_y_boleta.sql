ALTER TABLE pagos
    ADD COLUMN medio_pago VARCHAR(80) NULL;

UPDATE pagos
SET medio_pago = CONCAT(
    'tarjeta de ',
    CASE WHEN LOWER(tipo_tarjeta) = 'credito' THEN 'crédito' ELSE 'débito' END,
    CASE LOWER(marca_tarjeta)
        WHEN 'visa' THEN ' VISA'
        WHEN 'mastercard' THEN ' Mastercard'
        WHEN 'amex' THEN ' AMEX'
        WHEN 'american express' THEN ' AMEX'
        ELSE ''
    END,
    ' •••• ',
    ultimos_cuatro
);

ALTER TABLE pagos
    MODIFY COLUMN medio_pago VARCHAR(80) NOT NULL,
    DROP COLUMN cliente_id,
    DROP COLUMN tipo_tarjeta,
    DROP COLUMN marca_tarjeta,
    DROP COLUMN ultimos_cuatro;

ALTER TABLE boletas
    DROP INDEX idx_boleta_cita_cliente,
    DROP COLUMN cliente_id,
    ADD INDEX idx_boleta_cita (cita_id);
