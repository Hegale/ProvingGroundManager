package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Car;
import pg.provingground.domain.Ground;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroundRepositoryImpl {

    private final EntityManager em;

    public Ground findOne(Long id) {
        return em.find(Ground.class, id);
    }

    /** 이미 데이터베이스에 해당 이름의 시험장이 존재하는지 조회 */
    public boolean isDuplicateGroundName(String groundName) {
        return !(em.createQuery(
                        "SELECT g " +
                                "FROM Ground g " +
                                "WHERE g.name like :name",
                        Ground.class)
                .setParameter("name", groundName)
                .getResultList().isEmpty());
    }

}
