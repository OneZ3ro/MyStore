package MyStore.repositories;

import MyStore.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findBySigla(String sigla);
    Optional<Province> findByName(String name);
}