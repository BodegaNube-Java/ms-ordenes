package cl.duoc.ms_ordenes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrdenCreateRequestDto(
    @NotBlank(message = "El ID de la orden externa es obligatorio")
    String codigoPedido,

    @NotBlank(message = "El ID del comercio es obligatorio")
    String comercioId,

    @NotEmpty(message = "La orden debe contener al menos un ítem")
    @Valid
    List<ItemOrdenDto> items
) {}