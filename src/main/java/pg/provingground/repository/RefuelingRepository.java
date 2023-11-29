package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Refueling;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RefuelingRepository {

    private final EntityManager em;

    public void save(Refueling refueling) {
        em.persist(refueling);
    }

    public Refueling findOne(Long id) {
        return em.find(Refueling.class, id);
    }

    // TODO: 각 검색조건을 바탕으로 검색하기

}
