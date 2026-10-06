package cl.duoc.ms_ordenes.controller;

import cl.duoc.ms_ordenes.dto.OrdenCreateRequestDto;
import cl.duoc.ms_ordenes.dto.OrdenResponseDto;
import cl.duoc.ms_ordenes.service.OrdenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    // Recibe peticiones desde API Gateway / Lambda
    @PostMapping
    public ResponseEntity<OrdenResponseDto> crearOrden(@Valid @RequestBody OrdenCreateRequestDto request) {
        OrdenResponseDto respuesta = ordenService.crearOrden(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // Endpoint expuesto para el servicio de picking
    @GetMapping("/picking-disponibles")
    public ResponseEntity<List<OrdenResponseDto>> obtenerOrdenesParaPicking() {
        List<OrdenResponseDto> ordenes = ordenService.obtenerOrdenesDisponiblesParaPicking();
        return ResponseEntity.ok(ordenes);
    }
}