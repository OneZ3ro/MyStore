package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private UUID orderId;
    @ManyToMany
    @JoinTable(name = "orders_products", joinColumns = @JoinColumn(name = "orders_id"), inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;
    private double total;
    private LocalDate dateOfOrder;
    private LocalDate dateOfDelivery;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
