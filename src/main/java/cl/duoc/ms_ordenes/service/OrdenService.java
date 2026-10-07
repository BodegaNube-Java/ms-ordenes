package cl.duoc.ms_ordenes.service;

import cl.duoc.ms_ordenes.model.Orden;
import cl.duoc.ms_ordenes.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private WebClient inventarioWebClient;

    public Orden crearOrden(Orden orden) {
        // 1. Guardar con estado inicial PENDIENTE_STOCK
        orden.setEstado("PENDIENTE_STOCK");
        Orden ordenGuardada = ordenRepository.save(orden);

        try {
            // 2. Intentar reservar en ms-inventario
            inventarioWebClient.post()
                    .uri("/inventario/reservar")
                    .bodyValue(Map.of(
                            "ordenId", ordenGuardada.getId(),
                            "items", ordenGuardada.getItems()
                    ))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            // 3. Si responde 200 OK (o 200 Idempotente), actualizamos a LISTA_PARA_PICKING
            ordenGuardada.setEstado("LISTA_PARA_PICKING");
            return ordenRepository.save(ordenGuardada);

        } catch (WebClientResponseException.Conflict ex) {
            // Regla de Negocio (HTTP 409): No hay stock suficiente.
            // Retorna la orden guardada como PENDIENTE_STOCK.
            // Al retornar con éxito hacia el Controller, el endpoint responde HTTP 201 a Lambda.
            // Lambda entiende que la regla se procesó y borra el mensaje de SQS (Punto 4 del Contrato).
            return ordenGuardada;

        } catch (WebClientResponseException.InternalServerError | 
                 WebClientResponseException.ServiceUnavailable ex) {
            // Fallo técnico (HTTP 5xx / Timeout): Lanza la excepción hacia el Controller.
            // Esto devolverá HTTP 500 a Lambda para que reintente el mensaje y eventualmente caiga a la DLQ.
            throw ex;
        }
    }

    public List<Orden> obtenerOrdenesDisponibles() {
        return ordenRepository.findByEstado("LISTA_PARA_PICKING");
    }
}