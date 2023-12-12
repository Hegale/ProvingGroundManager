package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Test;
import pg.provingground.domain.User;
import pg.provingground.dto.history.TestHistory;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestRepository {

    private final EntityManager em;

    public void save(Test test) {
        em.persist(test);
    }

    public List<TestHistory> findAllByUser(User user) {
        return em.createQuery(
                "SELECT new pg.provingground.dto.TestHistory(" +
                        "t.testId, t.dateTime, t.groundRental.ground.name, t.carCount, t.partners, t.title) " +
                        "FROM Test t " +
                        "WHERE t.user = :user", TestHistory.class)
                .setParameter("user", user)
                .getResultList();
    }
}
