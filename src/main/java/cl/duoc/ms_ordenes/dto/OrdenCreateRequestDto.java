package cl.duoc.ms_ordenes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCreateRequestDto {

    @NotBlank(message = "El ID de la orden externa es obligatorio")
    private String externalOrderId;

    @NotBlank(message = "El ID del comercio es obligatorio")
    private String comercioId;

    @NotEmpty(message = "La orden debe contener al menos un ítem")
    private List<ItemRequestDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemRequestDto {
        @NotBlank(message = "El productoId es obligatorio")
        private String productoId;

        private Integer cantidad;
    }
}