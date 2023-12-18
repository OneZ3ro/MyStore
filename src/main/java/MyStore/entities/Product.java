package MyStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"userSeller"})
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "products_id")
    private UUID productId;
    private String name;
    @Column(name = "img_url")
    private String imgUrl;
    private double stars;
    private long reviews;
    private double price;
    @Column(name = "list_price")
    private double listPrice;
    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory;
    @Column(name = "best_seller")
    private boolean bestSeller;
    @Column(name = "bought_in_last_month")
    private long boughtInLastMonth;
    private String seller;
    @ManyToOne
    @JoinColumn(name = "user_seller_id", nullable = false)
    private User userSeller;

    public Product(String name, String imgUrl, double stars, long reviews, double price, double listPrice, SubCategory subCategory, boolean bestSeller, long boughtInLastMonth, String seller, User userSeller) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.stars = stars;
        this.reviews = reviews;
        this.price = price;
        this.listPrice = listPrice;
        this.subCategory = subCategory;
        this.bestSeller = bestSeller;
        this.boughtInLastMonth = boughtInLastMonth;
        this.seller = seller;
        this.userSeller = userSeller;
    }
}
