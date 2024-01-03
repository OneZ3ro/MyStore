package MyStore.repositories;

import MyStore.entities.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    Optional<List<Municipality>> findByCap (String cap);
    Optional<Municipality> findByName (String name);
}
