package cl.duoc.ms_ordenes.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    String mensaje,
    List<String> detalles,
    LocalDateTime timestamp
) {
    public static ErrorResponse de(String mensaje, List<String> detalles) {
        return new ErrorResponse(mensaje, detalles, LocalDateTime.now());
    }

    public static ErrorResponse de(String mensaje) {
        return new ErrorResponse(mensaje, List.of(), LocalDateTime.now());
    }
}