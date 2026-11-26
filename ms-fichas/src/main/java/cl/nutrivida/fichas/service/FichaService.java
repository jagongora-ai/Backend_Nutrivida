package cl.nutrivida.fichas.service;

import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import cl.nutrivida.fichas.dto.FichaRequest;
import cl.nutrivida.fichas.dto.FichaResponse;
import cl.nutrivida.fichas.exception.FichaNoEncontradaException;
import cl.nutrivida.fichas.exception.RutDuplicadoException;
import cl.nutrivida.fichas.model.FichaPaciente;
import cl.nutrivida.fichas.repository.FichaRepository;

@Service
@Transactional
public class FichaService {
    private final FichaRepository repository;

    public FichaService(FichaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<FichaResponse> listar(String nombre) {
        List<FichaPaciente> fichas = StringUtils.hasText(nombre)
            ? repository.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre.trim())
            : repository.findAllByOrderByNombreAsc();
        return fichas.stream().map(FichaResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public FichaResponse obtener(Long id) {
        return FichaResponse.from(buscar(id));
    }

    public FichaResponse crear(FichaRequest request, Long idMedico) {
        String rut = normalizarRut(request.rut());
        if (repository.existsByRutIgnoreCase(rut)) {
            throw new RutDuplicadoException();
        }
        FichaPaciente ficha = new FichaPaciente();
        aplicar(ficha, request, rut);
        ficha.setIdMedico(idMedico);
        return FichaResponse.from(repository.save(ficha));
    }

    public FichaResponse actualizar(Long id, FichaRequest request) {
        FichaPaciente ficha = buscar(id);
        String rut = normalizarRut(request.rut());
        if (repository.existsByRutIgnoreCaseAndIdNot(rut, id)) {
            throw new RutDuplicadoException();
        }
        aplicar(ficha, request, rut);
        return FichaResponse.from(repository.save(ficha));
    }

    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }

    private FichaPaciente buscar(Long id) {
        return repository.findById(id).orElseThrow(FichaNoEncontradaException::new);
    }

    private void aplicar(FichaPaciente ficha, FichaRequest request, String rut) {
        ficha.setNombre(request.nombre().trim());
        ficha.setRut(rut);
        ficha.setEdad(request.edad());
        ficha.setFechaNacimiento(request.fechaNacimiento());
        ficha.setTelefono(request.telefono().trim());
        ficha.setCorreo(request.correo().trim().toLowerCase());
        ficha.setEnfermedades(request.enfermedades().trim());
        ficha.setAlergias(request.alergias().trim());
        ficha.setMedicamentos(request.medicamentos().trim());
        ficha.setAlimentacion(request.alimentacion().trim());
        ficha.setComidas(request.comidas());
        ficha.setAgua(request.agua());
        ficha.setActividad(request.actividad().trim());
        ficha.setPeso(request.peso());
        ficha.setEstatura(request.estatura());
        ficha.setImc(request.peso().divide(request.estatura().multiply(request.estatura()), 2, RoundingMode.HALF_UP));
        ficha.setObjetivo(request.objetivo().trim());
        ficha.setObservaciones(request.observaciones().trim());
        ficha.setRecomendaciones(request.recomendaciones().trim());
    }

    private String normalizarRut(String rut) {
        return rut.trim().toUpperCase();
    }
}
