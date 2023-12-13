package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;

import java.util.List;

@Repository
@RequiredArgsConstructor
// TODO: 양방향 관계 고려하기. 상당수가 필요없어짐
public class CarRepository {

    private final EntityManager em;

    public void save(Car car) {
        em.persist(car);
    }

    public Car findOne(Long id) {
        return em.find(Car.class, id);
    }

    public List<Car> findAll() {
        return em.createQuery("select c from Car c order by c.fuel", Car.class).getResultList();
    }

    /** 차량번호를 통한 차량 검색 */
    public List<Car> findByNumber(String number) {
        return em.createQuery(
                "SELECT c " +
                        "FROM Car c " +
                        "JOIN FETCH c.type t " +
                        "WHERE c.number like :number " +
                        "ORDER BY c.fuel",
                Car.class)
                .setParameter("number", "%" + number + "%")
                .getResultList();
    }

    /** 이미 데이터베이스에 해당 차량번호의 차량이 존재하는지 조회 */
    public boolean isDuplicateCarNumber(String carNumber) {
        return !(em.createQuery(
                        "SELECT c " +
                                "FROM Car c " +
                                "WHERE c.number like :number",
                                Car.class)
                .setParameter("number", carNumber)
                .getResultList().isEmpty());
    }

    /** 차종을 통한 차량 검색 */
    public List<Car> findByCarTypes(List<CarType> types) {
        return em.createQuery(
                "SELECT c " +
                        "FROM Car c " +
                        "JOIN FETCH c.type t " +
                        "WHERE t IN :types", Car.class)
                .setParameter("types", types)
                .getResultList();
    }

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
    public List<Car> findByCarType(Long typeId) {
        return em.createQuery(
                "SELECT c " +
                        "FROM Car c " +
                        "JOIN FETCH c.type ct " +
                        "WHERE ct.carTypeId = :typeId", Car.class)
                .setParameter("typeId", typeId)
                .getResultList();
    }

}
