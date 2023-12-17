package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.CarRentalDto;
import pg.provingground.dto.admin.CarRentalSearchForm;
import pg.provingground.dto.history.CarRentalHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<CarRental> findByIds(List<Long> ids) {
        return em.createQuery("select c from CarRental c where c.id in :ids", CarRental.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    public List<CarRental> findByTest(Test test) {
        return em.createQuery("SELECT c FROM CarRental c WHERE c.test = :test", CarRental.class)
                .setParameter("test", test)
                .getResultList();
    }

    /** 특정 유저의 대여 내역을 검색 */
    public List<CarRental> findAllByUser(User user) {
        return em.createQuery(
                        "select c from CarRental c where c.user = :user", CarRental.class)
                .setParameter("user", user)
                .getResultList();
    }

    /** 특정 유저의 대여 내역을 시간 간격으로 검색 */
    public List<CarRental> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createQuery(
                        "SELECT c " +
                                "FROM CarRental c " +
                                "WHERE c.user = :user AND c.startTime BETWEEN :startDate AND :endDate " +
                                "ORDER BY c.startTime DESC",
                        CarRental.class)
                .setParameter("user", user)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<CarRentalHistory> findAllByUserAndTime(User user, LocalDateTime dateTime) {
        return em.createQuery(
                        "select new pg.provingground.dto.history.CarRentalHistory(c.carRentalId, c.car.type.name, c.startTime) " +
                                "from CarRental c " +
                                "where c.user = :user " +
                                "and c.startTime = :dateTime", CarRentalHistory.class)
                .setParameter("user", user)
                .setParameter("dateTime", dateTime)
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

    /** 해당 차종의 해당 날짜 예약건들을 <시간별, 예약건수> 로 반환 */
    public Map<LocalTime, Long> findAvailableTimesCount(Long carTypeId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<Object[]> results = em.createQuery(
                        "SELECT c.startTime, COUNT(c) " +
                                "FROM CarRental c " +
                                "WHERE c.startTime BETWEEN :start AND :end AND c.car.type.carTypeId = :carTypeId " +
                                "AND c.returned = 'N' " +
                                "GROUP BY c.startTime", Object[].class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .setParameter("carTypeId", carTypeId)
                .getResultList();

        Map<LocalTime, Long> countByTime = new HashMap<>();
        for (Object[] result : results) {
            LocalTime time = ((LocalDateTime) result[0]).toLocalTime();
            Long count = (Long) result[1];
            countByTime.put(time, count);
        }

        return countByTime;
    }

    /** 해당 차종의 해당 시간대 예약 불가능 차량 반환 */
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

    // === 관리자 검색 메서드 === //

    /** [관리자] CarRentalSearchForm의 조건에 따른 검색 반환 */
    public List<CarRentalDto> searchCarRentals(CarRentalSearchForm searchForm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CarRentalDto> cq = cb.createQuery(CarRentalDto.class);
        Root<CarRental> carRental = cq.from(CarRental.class);

        Join<CarRental, Car> car = carRental.join("car", JoinType.LEFT);
        Join<Car, CarType> carType = car.join("type", JoinType.LEFT); // Car와 CarType 조인
        Join<CarRental, User> user = carRental.join("user", JoinType.LEFT);

        cq.select(cb.construct(
                CarRentalDto.class,
                carRental.get("carRentalId"),
                carType.get("name"), // CarType의 'name' 필드 접근
                car.get("number"),
                user.get("username"),
                carRental.get("startTime"),
                carRental.get("returned")
        ));

        List<Predicate> predicates = buildWhereClause(cb, carRental, car, carType, user, searchForm);

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    private List<Predicate> buildWhereClause(CriteriaBuilder cb, Root<CarRental> carRental, Join<CarRental, Car> car, Join<Car, CarType> carType, Join<CarRental, User> user, CarRentalSearchForm searchForm) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchForm.getCarRentalId() != null) {
            predicates.add(cb.equal(carRental.get("carRentalId"), searchForm.getCarRentalId()));
        }
        if (StringUtils.hasText(searchForm.getCarName())) {
            predicates.add(cb.like(carType.get("name"), "%" + searchForm.getCarName() + "%"));
        }
        if (StringUtils.hasText(searchForm.getCarNumber())) {
            predicates.add(cb.like(car.get("number"), "%" + searchForm.getCarNumber() + "%"));
        }
        if (StringUtils.hasText(searchForm.getUsername())) {
            predicates.add(cb.like(user.get("username"), "%" + searchForm.getUsername() + "%"));
        }
        if (searchForm.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(carRental.get("startTime"), searchForm.getStartDateTime()));
        }
        if (searchForm.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(carRental.get("startTime"), searchForm.getEndDateTime()));
        }

        return predicates;
    }


}
