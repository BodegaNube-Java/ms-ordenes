package cl.duoc.ms_ordenes.dto;

import java.util.List;
import java.util.UUID;

public record ReservaStockRequestDto(
    UUID ordenId,
    List<ItemOrdenDto> items
) {}