package com.example.ms_agendar.model;

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
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table(name = "Prestacion")
public class Prestacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @Column(name = "valor", nullable = false, unique = true, length = 40)
    private String valor;
 
    @Column(name = "etiqueta", nullable = false, length = 60)
    private String etiqueta;
 
    @Column(name = "precio", nullable = false)
    private Integer precio;
 
    @Column(name = "duracion", nullable = false, length = 20)
    private String duracion;
}

