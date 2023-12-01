package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CarRentalRepository {

    private final EntityManager em;

    public void save(CarRental carRental) {
        em.persist(carRental);
    }

    public List<CarRental> findAll() {
        return em.createQuery("select c from CarRental c", CarRental.class).getResultList();
    }

    public CarRental findOne(Long id) {
        return em.find(CarRental.class, id);
    }

    // TODO: 성능 생각하기. 내일 이후만 검색
    /** 인자로 받은 차량 중 반납되지 않은 대여기록 반환. 일단 아래걸로 대체 */
    public List<CarRental> findNotReturned(Long carTypeId) {
        String jpql = "select cr from CarRental cr where cr.returned = 'N' and cr.car = :carTypeId";
        return em.createQuery(jpql, CarRental.class)
                .setParameter("carTypeId", carTypeId)
                .getResultList();
    }

    /** 시간대별로 차량 대여 횟수 반환. */
    public Map<LocalDateTime, Integer> countRentedCarsPerTimeSlot(Long carTypeId) {
        // 예약 가능일: 예약 시점 다음날 ~ +30일
        LocalDateTime start = LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(30);
        List<Object[]> results = em.createQuery(
                "SELECT c.startTime, COUNT(c) " +
                        "FROM CarRental c " +
                        "WHERE c.startTime > :start and c.startTime < :end " +
                        "AND c.returned  = 'N' and c.car = :carTypeId " +
                        "GROUP BY c.startTime",
                Object[].class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("carTypeId", carTypeId)
                .getResultList();

        Map<LocalDateTime, Integer> rentedCarsPerTimeSlot = new HashMap<>();
        for (Object[] result : results) {
            rentedCarsPerTimeSlot.put(
                    (LocalDateTime) result[0],
                    ((Long) result[1]).intValue());
        }
        return rentedCarsPerTimeSlot;
    }

}
