package cl.nutrivida.pagos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import cl.nutrivida.pagos.dto.BoletaResponse;
import cl.nutrivida.pagos.dto.PagoRequest;
import cl.nutrivida.pagos.service.PagoService;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Simulación de pagos de citas y emisión de boletas.")
public class PagoController {
    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Pagar cita", description = "Consulta la cita en ms-agenda, la marca pagada y emite una boleta. Solo acepta metadatos de tarjeta; no enviar número completo, vencimiento ni CVV.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago simulado y boleta emitida"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o propiedades de tarjeta no permitidas"),
        @ApiResponse(responseCode = "404", description = "Cita inexistente"),
        @ApiResponse(responseCode = "409", description = "La cita ya tiene un pago"),
        @ApiResponse(responseCode = "503", description = "ms-agenda no disponible")
    })
    public BoletaResponse pagar(@Valid @RequestBody PagoRequest request) {
        return service.pagar(request);
    }
}
