package cl.duocuc.msprofesionales.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.duocuc.msprofesionales.dto.MensajeResponse;
import cl.duocuc.msprofesionales.dto.ProfesionalRequest;
import cl.duocuc.msprofesionales.dto.ProfesionalResponse;
import cl.duocuc.msprofesionales.exception.ConflictoException;
import cl.duocuc.msprofesionales.exception.RecursoNoEncontradoException;
import cl.duocuc.msprofesionales.model.Profesional;
import cl.duocuc.msprofesionales.repository.ProfesionalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfesionalService {

    private final ProfesionalRepository repository;

    private ProfesionalResponse response(Profesional profesional) {
        return new ProfesionalResponse(
                profesional.getId(),
                profesional.getNombre(),
                profesional.getEspecialidad(),
                profesional.getImagen(),
                profesional.getUbicacion(),
                profesional.getAtencion(),
                profesional.getExperiencia(),
                profesional.getSobreMi(),
                new ArrayList<>(profesional.getEstudios()),
                new ArrayList<>(profesional.getAreas()));
    }

    private void copiarDatos(ProfesionalRequest request, Profesional profesional) {
        profesional.setNombre(request.getNombre().trim());
        profesional.setEspecialidad(request.getEspecialidad().trim());
        profesional.setImagen(request.getImagen().trim());
        profesional.setUbicacion(request.getUbicacion().trim());
        profesional.setAtencion(request.getAtencion().trim());
        profesional.setExperiencia(request.getExperiencia().trim());
        profesional.setSobreMi(request.getSobreMi() == null ? "" : request.getSobreMi().trim());
        profesional.setEstudios(new ArrayList<>(request.getEstudios()));
        profesional.setAreas(new ArrayList<>(request.getAreas()));
    }

    private Profesional buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesional no encontrado"));
    }

    public List<ProfesionalResponse> listar(String busqueda) {
        List<Profesional> profesionales;
        if (busqueda == null || busqueda.isBlank()) {
            profesionales = repository.findAllByOrderByNombreAsc();
        } else {
            String texto = busqueda.trim();
            profesionales = repository
                    .findByNombreContainingIgnoreCaseOrEspecialidadContainingIgnoreCaseOrderByNombreAsc(texto, texto);
        }

        List<ProfesionalResponse> resultado = new ArrayList<>();
        for (Profesional profesional : profesionales) {
            resultado.add(response(profesional));
        }
        return resultado;
    }

    public ProfesionalResponse obtener(Long id) {
        return response(buscar(id));
    }

    public ProfesionalResponse crear(ProfesionalRequest request) {
        if (repository.existsByNombreIgnoreCase(request.getNombre().trim())) {
            throw new ConflictoException("Ya existe un profesional con ese nombre");
        }

        Profesional profesional = new Profesional();
        copiarDatos(request, profesional);

        Profesional guardado = repository.save(profesional);
        return response(guardado);
    }

    public ProfesionalResponse actualizar(Long id, ProfesionalRequest request) {
        Profesional profesional = buscar(id);

        if (repository.existsByNombreIgnoreCaseAndIdNot(request.getNombre().trim(), id)) {
            throw new ConflictoException("Ya existe un profesional con ese nombre");
        }

        copiarDatos(request, profesional);

        Profesional guardado = repository.save(profesional);
        return response(guardado);
    }

    public MensajeResponse eliminar(Long id) {
        Profesional profesional = buscar(id);
        repository.delete(profesional);
        return new MensajeResponse("Medico eliminado con exito");
    }
}
