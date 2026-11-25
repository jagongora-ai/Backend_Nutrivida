package cl.nutrivida.fichas.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import cl.nutrivida.fichas.dto.FichaRequest;
import cl.nutrivida.fichas.dto.FichaResponse;
import cl.nutrivida.fichas.dto.MensajeResponse;
import cl.nutrivida.fichas.service.FichaService;

@RestController
@RequestMapping("/api/fichas")
@Validated
@Tag(name = "Fichas clínicas", description = "Gestión de fichas de pacientes asociadas a un identificador de médico.")
public class FichaController {
    private final FichaService service;

    public FichaController(FichaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar fichas", description = "Devuelve todas las fichas ordenadas por nombre; permite filtrar por nombre.")
    @ApiResponse(responseCode = "200", description = "Fichas obtenidas correctamente")
    public List<FichaResponse> listar(@Parameter(description = "Filtra por nombre, sin distinguir mayúsculas")
                                      @RequestParam(required = false) String nombre) {
        return service.listar(nombre);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener ficha", description = "Busca una ficha por su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ficha encontrada"),
        @ApiResponse(responseCode = "404", description = "Ficha inexistente")
    })
    public FichaResponse obtener(@Parameter(description = "Identificador de la ficha", example = "1")
                                 @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear ficha", description = "Registra una ficha, almacena el médico creador y calcula el IMC.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ficha creada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe ese RUT")
    })
    public FichaResponse crear(@Valid @RequestBody FichaRequest request,
                               @Parameter(description = "Identificador numérico del médico (solo asociación; no autentica)")
                               @RequestHeader("X-Id-Medico") @NotNull @Positive(message = "X-Id-Medico debe ser positivo") Long idMedico) {
        return service.crear(request, idMedico);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ficha", description = "Actualiza una ficha por su identificador y recalcula el IMC.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ficha actualizada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Ficha inexistente o fuera del ámbito del médico"),
        @ApiResponse(responseCode = "409", description = "Ya existe ese RUT")
    })
    public FichaResponse actualizar(@Parameter(description = "Identificador de la ficha", example = "1")
                                    @PathVariable @Positive(message = "El id debe ser positivo") Long id,
                                    @Valid @RequestBody FichaRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Eliminar ficha", description = "Elimina una ficha por su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ficha eliminada"),
        @ApiResponse(responseCode = "404", description = "Ficha inexistente")
    })
    public MensajeResponse eliminar(@Parameter(description = "Identificador de la ficha", example = "1")
                                    @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        service.eliminar(id);
        return new MensajeResponse("Ficha eliminada con éxito");
    }
}
