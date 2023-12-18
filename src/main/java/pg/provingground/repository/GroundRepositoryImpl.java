package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Ground;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroundRepositoryImpl {

    private final EntityManager em;

    public Ground findOne(Long id) {
        return em.find(Ground.class, id);
    }


}
