package MyStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "locations_id")
    private UUID locationId;
    @ManyToOne
    @JoinColumn(name = "municipalities_id")
    private Municipality municipality;
    private String address;
}
