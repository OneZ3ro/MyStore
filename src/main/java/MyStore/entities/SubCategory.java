package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sub_categories")
@NoArgsConstructor
@Getter
@Setter
public class SubCategory {
    @Id
    @Column(name = "sub_categories_id")
    private long subCategoryId;
    private String name;
    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;

    public SubCategory(long subCategoryId, String name, MainCategory mainCategory) {
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.mainCategory = mainCategory;
    }
}
