package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.GroundRental;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroundRentalRepository {
    private final EntityManager em;

    public void save(GroundRental groundRental) {
        em.persist(groundRental);
    }

    public GroundRental findOne(Long id) {
        return em.find(GroundRental.class, id);
    }

    /**
     * 인자로 받은 차량 중 반납되지 않은 대여기록 반환.
     */
    public List<GroundRental> findNotReturned(Long groundId) {
        String jpql = "select gr from GroundRental gr where gr.returned = 'N' and gr.ground = :groundId";
        return em.createQuery(jpql, GroundRental.class)
                .setParameter("groundId", groundId)
                .getResultList();
    }
}