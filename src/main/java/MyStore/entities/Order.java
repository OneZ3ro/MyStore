package MyStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"user"})
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private UUID orderId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_products", joinColumns = @JoinColumn(name = "orders_id"), inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;
    private double total;
    private LocalDate dateOfOrder;
    private LocalDate dateOfDelivery;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
