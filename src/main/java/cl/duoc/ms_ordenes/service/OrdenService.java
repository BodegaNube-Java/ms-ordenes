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
 // Guarda con estado inicial PENDIENTE_STOCK
        orden.setEstado("PENDIENTE_STOCK");
        Orden ordenGuardada = ordenRepository.save(orden);

        try {
//Intentar reservar en ms-inventario
            inventarioWebClient.post()
                    .uri("/inventario/reservar")
                    .bodyValue(Map.of(
                            "ordenId", ordenGuardada.getId(),
                            "items", ordenGuardada.getItems()
                    ))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

// responde 200 OK se actualizaz a LISTA_PARA_PICKING
            ordenGuardada.setEstado("LISTA_PARA_PICKING");
            return ordenRepository.save(ordenGuardada);

        } catch (WebClientResponseException.Conflict ex) {
            
            return ordenGuardada;

        } catch (WebClientResponseException.InternalServerError | 
                 WebClientResponseException.ServiceUnavailable ex) {
           
            throw ex;
        }
    }

    public List<Orden> obtenerOrdenesDisponibles() {
        return ordenRepository.findByEstado("LISTA_PARA_PICKING");
    }
}