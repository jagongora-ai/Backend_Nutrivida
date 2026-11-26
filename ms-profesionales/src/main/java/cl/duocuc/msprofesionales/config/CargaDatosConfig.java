package cl.duocuc.msprofesionales.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duocuc.msprofesionales.model.Profesional;
import cl.duocuc.msprofesionales.repository.ProfesionalRepository;

@Configuration
public class CargaDatosConfig {

    // se guardan en este orden para que queden con los ids 1 a 4 (Carolina es la medica de prueba, id 1)
    @Bean
    CommandLineRunner initProfesionales(ProfesionalRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(nuevo("Nut. Carolina Fuentes M.", "Especialista en Nutrición y Pérdida de Peso",
                        "/ProfesionalesIMG/medica 1.jpg", "Más de 10 años en nutrición clínica",
                        "Diseña planes de alimentación equilibrados y personalizados para alcanzar y mantener un peso saludable de forma sostenible.",
                        List.of("Nutrición, Universidad de La Frontera", "Magíster en Nutrición Clínica, Universidad de Chile",
                                "Diplomado en Manejo Integral del Sobrepeso y Obesidad"),
                        List.of("Pérdida de peso", "Reeducación alimentaria", "Evaluación antropométrica")));

                repository.save(nuevo("Nut. Felipe Araya R.", "Nutrición Clínica Pediátrica y Familiar",
                        "/ProfesionalesIMG/medico 1.jpg", "Más de 8 años en nutrición clínica",
                        "Orientado al control y manejo de enfermedades metabólicas mediante una alimentación adecuada para cada etapa de vida.",
                        List.of("Nutrición Clínica, Universidad de La Frontera", "Especialización en Nutrición Clínica",
                                "Diplomado en Manejo de Enfermedades Metabólicas"),
                        List.of("Nutrición clínica", "Diabetes", "Salud familiar")));

                repository.save(nuevo("Nut. Daniela Morales C.", "Nutrición Vegetariana y Vegana",
                        "/ProfesionalesIMG/medica 2.jpg", "Más de 7 años en nutrición",
                        "Diseña planes vegetarianos y veganos equilibrados, sostenibles y adaptados a las necesidades de cada paciente.",
                        List.of("Nutrición, Universidad de Chile", "Especialización en Nutrición Vegetariana",
                                "Diplomado en Alimentación Vegana"),
                        List.of("Nutrición vegetariana", "Nutrición vegana", "Alimentación saludable")));

                repository.save(nuevo("Nut. Rodrigo Sepúlveda A.", "Nutrición Deportiva",
                        "/ProfesionalesIMG/medico 2.jpg", "Más de 9 años en nutrición deportiva",
                        "Acompaña a deportistas a mejorar su rendimiento, recuperación y alimentación integral.",
                        List.of("Nutrición, Universidad de La Frontera", "Especialización en Nutrición Deportiva",
                                "Diplomado en Rendimiento Deportivo"),
                        List.of("Nutrición deportiva", "Rendimiento físico", "Recuperación deportiva")));
            }
        };
    }

    private Profesional nuevo(String nombre, String especialidad, String imagen, String experiencia,
            String sobreMi, List<String> estudios, List<String> areas) {
        return new Profesional(null, nombre, especialidad, imagen, "Temuco, Chile", "Presencial y Online",
                experiencia, sobreMi, new ArrayList<>(estudios), new ArrayList<>(areas));
    }
}
