package cl.nutrivida.pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record PagoRequest(
    @NotNull(message = "El id de la cita es obligatorio")
    @Positive(message = "El id de la cita debe ser positivo")
    Long citaId,
    @NotBlank(message = "El medio de pago es obligatorio")
    @Pattern(
        regexp = "^tarjeta de (?:crédito|débito)(?: (?:VISA|Mastercard|AMEX))? •••• \\d{4}$",
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "El medio de pago debe incluir tipo de tarjeta y sus últimos cuatro dígitos."
    )
    String medioPago
) { }
