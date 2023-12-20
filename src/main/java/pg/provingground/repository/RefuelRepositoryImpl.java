package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RefuelRepositoryImpl {

    private final EntityManager em;

    public void save(Refuel refuel) {
        em.persist(refuel);
    }

    public Refuel findOne(Long id) {
        return em.find(Refuel.class, id);
    }

    public List<Refuel> findAll() {
        return em.createQuery("SELECT r FROM Refuel r", Refuel.class).getResultList();
    }

    public List<Refuel> findAllByUser(User user) {
        return em.createQuery("SELECT r FROM Refuel r WHERE r.user = :user", Refuel.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Refuel> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createQuery("SELECT r " +
                                "FROM Refuel r " +
                                "WHERE r.user = :user AND r.time BETWEEN :startDate AND :endDate " +
                                "ORDER BY r.time DESC",
                        Refuel.class)
                .setParameter("user", user)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    /** 특정 유저의 주유기록 검색 */
    public List<Refuel> findUserRefuel(Long userId) {
        return em.createQuery(
                        "SELECT r " +
                                "FROM Refuel r " +
                                "WHERE r.user.id = :userId", Refuel.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<RefuelDto> searchRefuels(RefuelSearchForm searchForm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RefuelDto> cq = cb.createQuery(RefuelDto.class);
        Root<Refuel> refuel = cq.from(Refuel.class);

        Join<Refuel, Car> car = refuel.join("car", JoinType.LEFT);
        Join<Car, CarType> carType = car.join("type", JoinType.LEFT); // CarType 조인
        Join<Refuel, Station> station = refuel.join("station", JoinType.LEFT);
        // User 조인이 필요한 경우 추가

        cq.select(cb.construct(
                RefuelDto.class,
                refuel.get("refuelingId"),
                carType.get("name"), // CarType의 'name' 필드
                car.get("number"), // Car의 'number' 필드
                station.get("fuelType"),
                station.get("name"),
                refuel.get("time"),
                refuel.get("amount")
                // user 정보 필요시 추가
        ));

        List<Predicate> predicates = buildPredicates(cb, refuel, car, carType, station, searchForm);
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Refuel> refuel, Join<Refuel, Car> car, Join<Car, CarType> carType, Join<Refuel, Station> station, RefuelSearchForm searchForm) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchForm.getRefuelId() != null) {
            predicates.add(cb.equal(refuel.get("refuelingId"), searchForm.getRefuelId()));
        }
        if (StringUtils.hasText(searchForm.getCarName())) {
            predicates.add(cb.like(carType.get("name"), "%" + searchForm.getCarName() + "%"));
        }
        if (StringUtils.hasText(searchForm.getCarNumber())) {
            predicates.add(cb.like(car.get("number"), "%" + searchForm.getCarNumber() + "%"));
        }
        if (searchForm.getFuelType() != null) {
            predicates.add(cb.equal(station.get("fuelType"), searchForm.getFuelType()));
        }
        if (StringUtils.hasText(searchForm.getStationName())) {
            predicates.add(cb.like(station.get("name"), "%" + searchForm.getStationName() + "%"));
        }
        if (StringUtils.hasText(searchForm.getStartDate())) {
            predicates.add(cb.greaterThanOrEqualTo(refuel.get("time"), searchForm.getStartDateTime()));
        }
        if (StringUtils.hasText(searchForm.getEndDate())) {
            predicates.add(cb.lessThanOrEqualTo(refuel.get("time"), searchForm.getEndDateTime()));
        }
        if (StringUtils.hasText(searchForm.getAmount())) {
            predicates.add(cb.equal(refuel.get("amount"), Long.parseLong(searchForm.getAmount())));
        }

        return predicates;
    }



}