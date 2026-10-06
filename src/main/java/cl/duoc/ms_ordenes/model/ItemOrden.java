package cl.duoc.ms_ordenes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_orden")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "producto_id", nullable = false)
    private String productoId;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id")
    private Orden orden;
}