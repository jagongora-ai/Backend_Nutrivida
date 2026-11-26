package com.example.ms_agendar.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_agendar.model.Prestacion;

public interface PrestacionRepository extends JpaRepository<Prestacion, Long> {

    Optional<Prestacion> findByValor(String valor);
}

