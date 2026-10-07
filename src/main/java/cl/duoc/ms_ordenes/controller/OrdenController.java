package cl.duoc.ms_ordenes.controller;

import cl.duoc.ms_ordenes.model.Orden;
import cl.duoc.ms_ordenes.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = "*")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // Endpoint para recibir la orden e intentar reservar stock en ms-inventario
    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden) {
        Orden nuevaOrden = ordenService.crearOrden(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
    }

    // Endpoint que consultará el microservicio de Picking y Despacho
    @GetMapping("/disponibles")
    public ResponseEntity<List<Orden>> obtenerOrdenesDisponibles() {
        List<Orden> ordenes = ordenService.obtenerOrdenesDisponibles();
        return ResponseEntity.ok(ordenes);
    }
}