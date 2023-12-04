package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CarRepository {

    private final EntityManager em;

    public void save(Car car) {
        em.persist(car);
    }

    public Car findOne(Long id) {
        return em.find(Car.class, id);
    }

    public List<Car> findAll() {
        return em.createQuery("select c from Car c", Car.class).getResultList();
    }

    @Transactional
    public void delete(Car car) {
        if (car != null) {
            em.remove(car);
        }
    }

    /** 특정 차종에 해당하는 차량의 대수 구하기 */
    public long countCarsPerCarType(Long carTypeId) {
        Long count = em.createQuery(
                "SELECT COUNT(c) " +
                        "FROM Car c " +
                        "WHERE c.type.carTypeId = :carTypeId",
                Long.class)
                .setParameter("carTypeId", carTypeId)
                .getSingleResult();

        return count != null ? count : 0;
    }

    /** 특정 차종의 차량들 검색 */
    public List<Car> findByCondition(CarType type) {
        return em.createQuery("select c from Car c where c.type = :type", Car.class)
                .setParameter("type", type)
                .getResultList();
    }

}
