package cl.nutrivida.pagos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import cl.nutrivida.pagos.dto.BoletaResponse;
import cl.nutrivida.pagos.service.PagoService;

@RestController
@RequestMapping("/api/boletas")
@Validated
@PreAuthorize("hasRole('Cliente')")
@Tag(name = "Boletas", description = "Consulta de boletas por cita.")
public class BoletaController {
    private final PagoService service;

    public BoletaController(PagoService service) {
        this.service = service;
    }

    @GetMapping("/cita/{citaId}")
    @Operation(summary = "Consultar boleta por cita", description = "Devuelve la boleta emitida para la cita asociada al identificador de cliente indicado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Boleta encontrada"),
        @ApiResponse(responseCode = "404", description = "No existe una boleta para esa cita")
    })
    public BoletaResponse obtener(@Parameter(description = "Identificador de la cita", example = "12")
                                  @PathVariable @Positive(message = "El id de la cita debe ser positivo") Long citaId) {
        return service.obtenerBoleta(citaId);
    }
}
