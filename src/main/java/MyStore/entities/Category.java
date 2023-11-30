package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private long categoryId;
    private String mainCategoryName;
    private List<String> subCategoryNames;

    public Category(String mainCategoryName, List<String> subCategoryNames) {
        this.mainCategoryName = mainCategoryName;
        this.subCategoryNames = subCategoryNames;
    }
}
