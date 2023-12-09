package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;

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

    public List<CarRental> findAllByUser(User user) {
        return em.createQuery(
                "select c from CarRental c where c.user = :user", CarRental.class)
                .setParameter("user", user)
                .getResultList();
    }

    public CarRental findOne(Long id) {
        return em.find(CarRental.class, id);
    }

    /** 차량 대여 내역의 대여자와 인자로 받은 유저가 일치하는지 확인 */
    public boolean isUserMatched(Long carRentalId, Long userId) {
        try {
            em.createQuery(
                            "SELECT r " +
                                    "FROM CarRental r " +
                                    "WHERE r.carRentalId = :carRentalId AND r.user.userId = :userId", CarRental.class)
                    .setParameter("carRentalId", carRentalId)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return true;
        } catch(NoResultException nre) {
            return false;
        }
    }

    /** 해당 차종의 해당 시간대 예약을 반환 */
    public List<Car> findUnavailableCars(Long carTypeId, LocalDateTime time) {
        return em.createQuery(
                "SELECT c.car " +
                        "FROM CarRental c " +
                        "WHERE c.startTime = :time AND c.car.type.carTypeId = :carTypeId " +
                        "AND c.returned = 'N'", Car.class)
                .setParameter("time", time)
                .setParameter("carTypeId", carTypeId)
                .getResultList();
    }

    /** 시간대별 차량 대여 횟수 반환 */
    public Map<LocalDateTime, Integer> countRentedCarsPerTimeSlot(Long carTypeId) {
        // 예약 가능일: 예약 시점 다음날 ~ +30일
        LocalDateTime start = LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(30);
        List<Object[]> results = em.createQuery(
                "SELECT c.startTime, COUNT(c) " +
                        "FROM CarRental c " +
                        "WHERE c.startTime > :start and c.startTime < :end " +
                        "AND c.returned  = 'N' and c.car.type.carTypeId = :carTypeId " +
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
