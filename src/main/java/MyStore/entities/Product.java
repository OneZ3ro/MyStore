package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "products_id")
    private UUID productId;
    private String name;
    @Column(name = "main_category")
    private String mainCategory;
    @Column(name = "sub_category")
    private String subCategory;
    private String image;
    private double rating;
    @Column(name = "numb_of_rating")
    private long numbOfRating;
    @Column(name = "discount_price")
    private double discountPrice;
    @Column(name = "actual_price")
    private double actualPrice;
    private String seller;

    public Product(String name, String mainCategory, String subCategory, String image, double rating, long numbOfRating, double discountPrice, double actualPrice) {
        this.name = name;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.image = image;
        this.rating = rating;
        this.numbOfRating = numbOfRating;
        this.discountPrice = discountPrice;
        this.actualPrice = actualPrice;
    }

    public Product(String name, String mainCategory, String subCategory, String image, double rating, long numbOfRating, double discountPrice, double actualPrice, String seller) {
        this(name, mainCategory, subCategory, image, rating, numbOfRating, discountPrice, actualPrice);
        this.seller = seller;
    }
}
