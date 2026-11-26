package com.example.ms_agendar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ms_agendar.model.Prestacion;
import com.example.ms_agendar.repository.PrestacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
 
    private final PrestacionRepository prestacionRepository;
 
    @Override
    public void run(String... args) {
 
        if (prestacionRepository.count() > 0) {
            log.info("Datos de prestaciones ya cargados. Se omite inicialización.");
            return;
        }
 
        log.info("Cargando datos iniciales de prestaciones...");
 
        Prestacion prestacion1 = new Prestacion();
        prestacion1.setValor("Consulta");
        prestacion1.setEtiqueta("Consulta Nutricionista");
        prestacion1.setPrecio(22000);
        prestacion1.setDuracion("45 min");
        prestacionRepository.save(prestacion1);
 
        Prestacion prestacion2 = new Prestacion();
        prestacion2.setValor("Plan especializado");
        prestacion2.setEtiqueta("Plan especializado");
        prestacion2.setPrecio(35000);
        prestacion2.setDuracion("60 min");
        prestacionRepository.save(prestacion2);
 
        Prestacion prestacion3 = new Prestacion();
        prestacion3.setValor("Evaluación");
        prestacion3.setEtiqueta("Evaluación antropométrica");
        prestacion3.setPrecio(18000);
        prestacion3.setDuracion("30 min");
        prestacionRepository.save(prestacion3);
 
        Prestacion prestacion4 = new Prestacion();
        prestacion4.setValor("Taller Grupal");
        prestacion4.setEtiqueta("Taller Grupal");
        prestacion4.setPrecio(12000);
        prestacion4.setDuracion("90 min");
        prestacionRepository.save(prestacion4);
 
        log.info("Datos iniciales cargados correctamente.");
    }
}
