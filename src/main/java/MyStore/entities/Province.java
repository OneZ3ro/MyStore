package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "provinces")
@NoArgsConstructor
@Getter
@Setter
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provinces_id")
    private long provinceId;
    private String name;
    private String region;
    @OneToMany(mappedBy = "province")
    private List<Municipality> municipalities;

    public Province(String name, String region) {
        this.name = name;
        this.region = region;
    }
}
