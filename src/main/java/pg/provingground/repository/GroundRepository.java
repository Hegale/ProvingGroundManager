package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import pg.provingground.domain.Ground;

import java.util.List;

public interface GroundRepository extends JpaRepository<Ground, Long> {

    public Ground findOne(Long id);

}
