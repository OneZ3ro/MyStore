package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "municipalities")
@NoArgsConstructor
@Getter
@Setter
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "municipalities_id")
    private long municipalityId;
    private String name;
    @ManyToOne
    @JoinColumn(name = "provinces_id")
    private Province province;
    private String cap;

    public Municipality(String name, Province province, String cap) {
        this.name = name;
        this.province = province;
        this.cap = cap;
    }
}
