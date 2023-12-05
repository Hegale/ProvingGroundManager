package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Refuel;

@Repository
@RequiredArgsConstructor
public class RefuelingRepository {

    private final EntityManager em;

    public void save(Refuel refueling) {
        em.persist(refueling);
    }

    public Refuel findOne(Long id) {
        return em.find(Refuel.class, id);
    }

    // TODO: 각 검색조건을 바탕으로 검색하기

}
