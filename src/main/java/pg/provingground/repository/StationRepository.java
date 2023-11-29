package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Station;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StationRepository {

    private final EntityManager em;

    public void save(Station station) {
        em.persist(station);
    }

    public Station findOne(Long id) {
        return em.find(Station.class, id);
    }

    public List<Station> findAll() {
        return em.createQuery("select s from Station s", Station.class).getResultList();
    }

    @Transactional
    public void delete(Station station) {
        if (station != null) {
            em.remove(station);
        }
    }
}