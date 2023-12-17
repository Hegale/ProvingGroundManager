package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Ground;
import pg.provingground.dto.admin.GroundForm;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroundRepository {

    private final EntityManager em;

    public void save(Ground ground) {
        em.persist(ground);
    }

    public Ground findOne(Long id) {
        return em.find(Ground.class, id);
    }

    public List<Ground> findAll() {
        return em.createQuery("select g from Ground g", Ground.class).getResultList();
    }

    public void delete(Ground ground) {
        if (ground != null) {
            em.remove(ground);
        }
    }

}
