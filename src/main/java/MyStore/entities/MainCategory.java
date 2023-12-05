package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "main_categories")
@NoArgsConstructor
@Getter
@Setter
public class MainCategory {
    @Id
    @Column(name = "main_categories_id")
    private long mainCategoryId;
    private String mainCategoryName;
    @OneToMany(mappedBy = "mainCategory")
    private List<SubCategory> subCategoryNames;

    public MainCategory(long mainCategoryId, String mainCategoryName) {
        this.mainCategoryId = mainCategoryId;
        this.mainCategoryName = mainCategoryName;
    }
}
