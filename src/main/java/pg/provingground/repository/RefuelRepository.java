package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Refuel;
import pg.provingground.domain.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RefuelRepository {

    private final EntityManager em;

    public void save(Refuel refuel) {
        em.persist(refuel);
    }

    public Refuel findOne(Long id) {
        return em.find(Refuel.class, id);
    }

    /** 특정 유저의 주유기록 검색 */
    public List<Refuel> findUserRefuel(Long userId) {
        return em.createQuery(
                        "SELECT r " +
                                "FROM Refuel r " +
                                "WHERE r.user.id = :userId", Refuel.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // TODO: 각 검색조건을 바탕으로 검색하기


}