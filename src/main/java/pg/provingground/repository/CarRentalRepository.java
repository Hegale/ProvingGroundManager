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

    // TODO: 성능 생각하기
    /** 인자로 받은 차량 중 반납되지 않은 대여기록 반환. */
    public List<CarRental> findNotReturned(Long carId) {
        String jpql = "select cr from CarRental cr where cr.returned = 'N' and cr.car = " + carId;
        return em.createQuery(jpql, CarRental.class)
                .getResultList();
    }


}
