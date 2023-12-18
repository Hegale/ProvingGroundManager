package pg.provingground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pg.provingground.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
    public Station findOne(Long id);
}
