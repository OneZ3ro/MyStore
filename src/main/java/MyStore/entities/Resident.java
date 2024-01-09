package MyStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "residents")
@NoArgsConstructor
@Getter
@Setter
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "residents_id")
    private long residentId;
    private String region;
    private String province;
    private String acronym;
    private String cap;
    private String municipality;
}
