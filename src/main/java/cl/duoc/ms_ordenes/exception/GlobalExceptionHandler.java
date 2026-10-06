package cl.duoc.ms_ordenes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "ERROR_VALIDACION");
        response.put("mensaje", "Error de validación en los datos enviados");

        Map<String, String> erroresCampos = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> erroresCampos.put(err.getField(), err.getDefaultMessage()));

        response.put("detalles", erroresCampos);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJson(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "JSON_INVALIDO");
        response.put("mensaje", "El JSON enviado en la petición es inválido");

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Map<String, Object>> handleWebClient(WebClientResponseException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "ERROR_INVENTARIO");
        response.put("mensaje", "Fallo en la comunicación con ms-inventario");
        response.put("status", ex.getStatusCode().value());

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "PETICION_INVALIDA");
        response.put("mensaje", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) throws Exception {
        if (ex.getClass().getName().contains("NoResourceFoundException") || 
            ex instanceof jakarta.servlet.ServletException) {
            throw ex;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("error", "INTERNAL_ERROR");
        response.put("mensaje", "Error interno del servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}