package cl.duoc.ms_ordenes.repository;

import cl.duoc.ms_ordenes.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, String> {

    Optional<Orden> codigoPedidoAndComercioId(String codigoPedido, String comercioId);

//  genera el SQL para obtener las órdenes por estado 
    List<Orden> findByEstado(String estado);
}