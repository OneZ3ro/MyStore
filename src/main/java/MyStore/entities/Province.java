package MyStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"municipalities"})
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provinces_id")
    private long provinceId;
    private String name;
    private String region;
    private String sigla;
    @OneToMany(mappedBy = "province")
    private List<Municipality> municipalities;

    public Province(String name, String region, String sigla) {
        this.name = name;
        this.region = region;
        this.sigla =  sigla;
    }
}