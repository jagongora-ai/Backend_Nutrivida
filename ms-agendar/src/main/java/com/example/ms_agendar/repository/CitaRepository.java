package com.example.ms_agendar.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_agendar.model.Cita;
import com.example.ms_agendar.model.EstadoCita;

public interface CitaRepository extends JpaRepository<Cita, Long> {
 
    boolean existsByDoctorIdAndFechaAndHoraAndEstadoIn(
        Long doctorId, LocalDate fecha, LocalTime hora, List<EstadoCita> estados);
        
    List<Cita> findByDoctorIdAndFechaAndEstadoIn(Long doctorId, LocalDate fecha, List<EstadoCita> estados);
 
    List<Cita> findByIdPacienteAndEstadoOrderByFechaAscHoraAsc(Long idPaciente, EstadoCita estado);
 
    List<Cita> findByDoctorIdAndEstadoOrderByFechaAscHoraAsc(Long doctorId, EstadoCita estado);
}
