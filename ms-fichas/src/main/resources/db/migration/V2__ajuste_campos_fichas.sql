UPDATE fichas_paciente
SET rut = UPPER(REPLACE(TRIM(rut), '.', '')),
    enfermedades = COALESCE(NULLIF(TRIM(enfermedades), ''), 'Ninguna'),
    alergias = COALESCE(NULLIF(TRIM(alergias), ''), 'Ninguna'),
    medicamentos = COALESCE(NULLIF(TRIM(medicamentos), ''), 'Ninguno'),
    alimentacion = CASE
        WHEN alimentacion IN ('Omnívora', 'Vegetariana', 'Vegana', 'Otra') THEN alimentacion
        ELSE 'Otra'
    END,
    comidas = CASE WHEN comidas REGEXP '^[0-9]+$' THEN CAST(comidas AS UNSIGNED) ELSE 1 END,
    agua = CASE WHEN agua REGEXP '^[0-9]+$' THEN CAST(agua AS UNSIGNED) ELSE 0 END,
    actividad = CASE
        WHEN actividad IN ('Sedentaria', 'Leve', 'Moderada', 'Intensa') THEN actividad
        ELSE 'Leve'
    END,
    objetivo = COALESCE(NULLIF(TRIM(objetivo), ''), 'Sin objetivo registrado'),
    observaciones = COALESCE(NULLIF(TRIM(observaciones), ''), 'Sin observaciones'),
    recomendaciones = COALESCE(NULLIF(TRIM(recomendaciones), ''), 'Sin recomendaciones');

ALTER TABLE fichas_paciente
    MODIFY COLUMN nombre VARCHAR(50) NOT NULL,
    MODIFY COLUMN rut VARCHAR(12) NOT NULL,
    MODIFY COLUMN telefono VARCHAR(9) NOT NULL,
    MODIFY COLUMN correo VARCHAR(100) NOT NULL,
    MODIFY COLUMN enfermedades VARCHAR(500) NOT NULL,
    MODIFY COLUMN alergias VARCHAR(500) NOT NULL,
    MODIFY COLUMN medicamentos VARCHAR(500) NOT NULL,
    MODIFY COLUMN alimentacion VARCHAR(15) NOT NULL,
    MODIFY COLUMN comidas INT NOT NULL,
    MODIFY COLUMN agua INT NOT NULL,
    MODIFY COLUMN actividad VARCHAR(15) NOT NULL,
    MODIFY COLUMN peso DECIMAL(6,2) NOT NULL,
    MODIFY COLUMN estatura DECIMAL(4,2) NOT NULL,
    MODIFY COLUMN objetivo VARCHAR(300) NOT NULL,
    MODIFY COLUMN observaciones VARCHAR(1000) NOT NULL,
    MODIFY COLUMN recomendaciones VARCHAR(1000) NOT NULL,
    MODIFY COLUMN id_medico BIGINT NOT NULL,
    ADD CONSTRAINT uk_ficha_rut UNIQUE (rut);
