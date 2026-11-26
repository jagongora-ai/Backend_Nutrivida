package com.example.ms_agendar.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ms_agendar.client.ProfesionalClient;
import com.example.ms_agendar.client.UsuarioClient;
import com.example.ms_agendar.dto.CitaResponse;
import com.example.ms_agendar.dto.CitaRequest;
import com.example.ms_agendar.dto.HorasOcupadasResponse;
import com.example.ms_agendar.dto.MensajeResponse;
import com.example.ms_agendar.dto.ProfesionalExterno;
import com.example.ms_agendar.dto.UsuarioExterno;
import com.example.ms_agendar.exception.AccesoDenegadoException;
import com.example.ms_agendar.exception.ClaveInternaInvalidaException;
import com.example.ms_agendar.exception.ConflictoException;
import com.example.ms_agendar.exception.RecursoNoEncontradoException;
import com.example.ms_agendar.exception.ReglaNegocioException;
import com.example.ms_agendar.model.Cita;
import com.example.ms_agendar.model.EstadoCita;
import com.example.ms_agendar.model.Prestacion;
import com.example.ms_agendar.repository.CitaRepository;
import com.example.ms_agendar.repository.PrestacionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CitaService {

    private static final LocalTime PRIMERA_HORA = LocalTime.of(8, 0);
    private static final LocalTime ULTIMA_HORA = LocalTime.of(19, 30);
    private static final List<EstadoCita> OCUPAN_HORA = List.of(EstadoCita.PENDIENTE, EstadoCita.PAGADA);
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private final CitaRepository citaRepository;
    private final PrestacionRepository prestacionRepository;
    private final ProfesionalClient profesionalClient;
    private final UsuarioClient usuarioClient;
    private final Clock clock;
    private final String claveInterna;

    public CitaService(CitaRepository citaRepository,
            PrestacionRepository prestacionRepository,
            ProfesionalClient profesionalClient,
            UsuarioClient usuarioClient,
            Clock clock,
            @Value("${servicios.clave-interna}") String claveInterna) {
        this.citaRepository = citaRepository;
        this.prestacionRepository = prestacionRepository;
        this.profesionalClient = profesionalClient;
        this.usuarioClient = usuarioClient;
        this.clock = clock;
        this.claveInterna = claveInterna;
    }

    @Transactional
    public CitaResponse crear(CitaRequest request, Long idPaciente) {
        log.info("Paciente {} reserva con doctor {} el {} a las {}",
                idPaciente, request.getDoctorId(), request.getFecha(), request.getHora());

        Prestacion prestacion = prestacionRepository.findByValor(request.getTipoConsulta())
                .orElseThrow(() -> new RecursoNoEncontradoException("La prestación no existe"));

        ProfesionalExterno profesional = profesionalClient.obtener(request.getDoctorId());

        UsuarioExterno paciente = usuarioClient.obtener(idPaciente);

        validarFechaYHora(request.getFecha(), request.getHora());

        if (citaRepository.existsByDoctorIdAndFechaAndHoraAndEstadoIn(
                request.getDoctorId(), request.getFecha(), request.getHora(), OCUPAN_HORA)) {
            throw new ConflictoException("Esa hora ya fue tomada por otro paciente, por favor elige otra");
        }

        Cita cita = new Cita();
        cita.setTipoConsulta(prestacion.getValor());
        cita.setPrestacion(prestacion.getEtiqueta());
        cita.setDuracion(prestacion.getDuracion());
        cita.setPrecio(prestacion.getPrecio());
        cita.setDoctorId(profesional.getId());
        cita.setDoctor(profesional.getNombre());
        cita.setEspecialidad(profesional.getEspecialidad());
        cita.setImagen(profesional.getImagen());
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setModalidad(request.getModalidad());
        cita.setIdPaciente(idPaciente);
        cita.setPaciente(paciente.getNombre());
        cita.setRut(paciente.getRut());
        cita.setCorreo(paciente.getCorreo());
        cita.setEstado(EstadoCita.PENDIENTE);
        cita.setCreadaEl(LocalDateTime.now(clock));

        return aResponse(citaRepository.save(cita));
    }

    private void validarFechaYHora(LocalDate fecha, LocalTime hora) {
        LocalDate hoy = LocalDate.now(clock);

        if (fecha.isBefore(hoy)) {
            throw new ReglaNegocioException("La fecha a seleccionar no puede ser de los días anteriores.");
        }
        if (hora.isBefore(PRIMERA_HORA) || hora.isAfter(ULTIMA_HORA)) {
            throw new ReglaNegocioException("Las horas de atención son de 8:00 AM a 20:00 PM.");
        }
        if (hora.getMinute() % 30 != 0 || hora.getSecond() != 0) {
            throw new ReglaNegocioException("Las horas se agendan en bloques de 30 minutos");
        }
        if (fecha.equals(hoy) && hora.isBefore(LocalTime.now(clock))) {
            throw new ReglaNegocioException("Esa hora ya pasó, por favor elige otra");
        }
    }

    public HorasOcupadasResponse horasOcupadas(Long doctorId, LocalDate fecha) {
        List<String> horas = citaRepository
                .findByDoctorIdAndFechaAndEstadoIn(doctorId, fecha, OCUPAN_HORA)
                .stream()
                .map(Cita::getHora)
                .sorted()
                .map(h -> h.format(FORMATO_HORA))
                .toList();
        return new HorasOcupadasResponse(doctorId, fecha, horas);
    }

    public CitaResponse obtener(Long id, Long idPaciente) {
        return aResponse(citaDelPaciente(id, idPaciente));
    }

    public List<CitaResponse> misCitas(Long idPaciente) {
        return citaRepository
                .findByIdPacienteAndEstadoOrderByFechaAscHoraAsc(idPaciente, EstadoCita.PAGADA)
                .stream().map(this::aResponse).toList();
    }

    public List<CitaResponse> agendaProfesional(Long profesionalId) {
        if (profesionalId == null) {
            throw new AccesoDenegadoException("Tu usuario no está asociado a un profesional");
        }
        return citaRepository
                .findByDoctorIdAndEstadoOrderByFechaAscHoraAsc(profesionalId, EstadoCita.PAGADA)
                .stream().map(this::aResponse).toList();
    }

    @Transactional
    public MensajeResponse cancelar(Long id, Long idPaciente) {
        Cita cita = citaDelPaciente(id, idPaciente);

        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new ConflictoException("Solo se puede cancelar una hora pendiente");
        }

        cita.setEstado(EstadoCita.CANCELADA);
        citaRepository.save(cita);
        return new MensajeResponse("Hora cancelada con éxito");
    }

    @Transactional
    public CitaResponse marcarPagada(Long id, String clave) {
        if (!claveValida(clave)) {
            throw new ClaveInternaInvalidaException("Clave interna inválida");
        }

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La hora no existe"));

        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new ConflictoException("La hora no está pendiente de pago");
        }

        cita.setEstado(EstadoCita.PAGADA);
        return aResponse(citaRepository.save(cita));
    }

    private Cita citaDelPaciente(Long id, Long idPaciente) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La hora no existe"));
        if (!cita.getIdPaciente().equals(idPaciente)) {
            throw new AccesoDenegadoException("No puedes ver una hora de otro paciente");
        }
        return cita;
    }

    private boolean claveValida(String clave) {
        return clave != null && MessageDigest.isEqual(
                clave.getBytes(StandardCharsets.UTF_8), claveInterna.getBytes(StandardCharsets.UTF_8));
    }

    private CitaResponse aResponse(Cita c) {
        return new CitaResponse(
                c.getId(),
                c.getTipoConsulta(),
                c.getPrestacion(),
                c.getDuracion(),
                c.getPrecio(),
                c.getDoctorId(),
                c.getDoctor(),
                c.getEspecialidad(),
                c.getImagen(),
                c.getFecha(),
                c.getModalidad(),
                c.getPaciente(),
                c.getRut(),
                c.getCorreo(),
                c.getEstado(),
                c.getHora());
    }
}
