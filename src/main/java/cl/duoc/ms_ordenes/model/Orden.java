package cl.duoc.ms_ordenes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(
    name = "ordenes",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_codigo_pedido", columnNames = {"codigo_pedido", "comercio_id"})
    }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "codigo_pedido", nullable = false)
    private String codigoPedido;

    @Column(name = "comercio_id", nullable = false)
    private String comercioId;

    @Column(nullable = false)
    private String estado; // PENDIENTE_STOCK, LISTA_PARA_PICKING

    @Column(name = "numero_seguimiento")
    private String numeroSeguimiento;

    @Version
    private Long version; // Control de concurrencia optimista

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonManagedReference
    private List<ItemOrden> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime ahora = LocalDateTime.now();
        this.createdAt = ahora;
        this.updatedAt = ahora;
        if (this.items != null) {
            this.items.forEach(item -> item.setOrden(this));
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}