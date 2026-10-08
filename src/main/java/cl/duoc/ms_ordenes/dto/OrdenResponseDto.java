package cl.duoc.ms_ordenes.dto;

import cl.duoc.ms_ordenes.model.Orden;

import java.time.LocalDateTime;
import java.util.List;

public record OrdenResponseDto(
    String id,
    String codigoPedido,
    String comercioId,
    String estado,
    String numeroSeguimiento,
    List<ItemOrdenDto> items,
    LocalDateTime createdAt
) {
    public static OrdenResponseDto desde(Orden orden) {
        List<ItemOrdenDto> itemsDto = orden.getItems() != null ?
                orden.getItems().stream()
                        .map(item -> new ItemOrdenDto(
                                String.valueOf(item.getProductoId()), 
                                item.getCantidad()
                        ))
                        .toList()
                : List.of();

        return new OrdenResponseDto(
                String.valueOf(orden.getId()),
                orden.getCodigoPedido(),
                orden.getComercioId(),
                orden.getEstado(),
                orden.getNumeroSeguimiento(),
                itemsDto,
                orden.getCreatedAt()
        );
    }
}