package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.CarRental;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarRentalRepository {

    private final EntityManager em;

    public void save(CarRental carRental) {
        em.persist(carRental);
    }

    public CarRental findOne(Long id) {
        return em.find(CarRental.class, id);
    }

    /*
    TODO: 조건에 맞는 객체 찾아 반환
    public List<CarRental> findNotReturned() {
        String jpql = "select cr from CarRental cr ";
    }
    */

}
