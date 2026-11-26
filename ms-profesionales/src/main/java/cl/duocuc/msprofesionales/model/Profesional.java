package cl.duocuc.msprofesionales.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profesionales")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profesional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(nullable = false, length = 120)
    private String especialidad;

    @Column(nullable = false, length = 255)
    private String imagen;

    @Column(nullable = false, length = 80)
    private String ubicacion;

    @Column(nullable = false, length = 40)
    private String atencion;

    @Column(nullable = false, length = 120)
    private String experiencia;

    @Column(nullable = true, length = 1000)
    private String sobreMi;

    // cada estudio se guarda en la tabla profesional_estudios
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profesional_estudios", joinColumns = @JoinColumn(name = "profesional_id"))
    @Column(name = "estudio", length = 200)
    private List<String> estudios = new ArrayList<>();

    // cada area se guarda en la tabla profesional_areas
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profesional_areas", joinColumns = @JoinColumn(name = "profesional_id"))
    @Column(name = "area", length = 100)
    private List<String> areas = new ArrayList<>();
}
