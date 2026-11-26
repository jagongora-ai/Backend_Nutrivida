package com.example.ms_agendar.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Entity
@Table(name = "citas")
@AllArgsConstructor
@NoArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_consulta", nullable = false, length = 40)
    private String tipoConsulta;
 
    @Column(name = "prestacion", nullable = false, length = 60)
    private String prestacion;
 
    @Column(name = "duracion", nullable = false, length = 20)
    private String duracion;
 
    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;
 
    @Column(name = "doctor", nullable = false, length = 50)
    private String doctor;
 
    @Column(name = "especialidad", nullable = false, length = 120)
    private String especialidad;
 
    @Column(name = "imagen", length = 255)
    private String imagen;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;
 
    @Column(name = "modalidad", nullable = false, length = 15)
    private String modalidad;

    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;
 
    @Column(name = "paciente", nullable = false, length = 50)
    private String paciente;
 
    @Column(name = "rut", nullable = false, length = 10)
    private String rut;
 
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;
 
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoCita estado;
 
    @Column(name = "creada_el", nullable = false)
    private LocalDateTime creadaEl;
}
