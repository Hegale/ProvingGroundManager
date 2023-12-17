package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.GroundRentalSearchForm;
import pg.provingground.dto.history.GroundRentalHistory;

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
public class GroundRentalRepository {
    private final EntityManager em;

    public void save(GroundRental groundRental) {
        em.persist(groundRental);
    }

    public GroundRental findOne(Long id) {
        return em.find(GroundRental.class, id);
    }

    public List<GroundRental> findAll() {
        return em.createQuery("select g from GroundRental g", GroundRental.class).getResultList();
    }

    public List<GroundRental> findAllByUser(User user) {
        return em.createQuery(
                "select g from GroundRental g where g.user = :user", GroundRental.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<GroundRental> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createQuery(
                        "SELECT g " +
                                "FROM GroundRental g " +
                                "WHERE g.user = :user AND g.startTime BETWEEN :startDate AND :endDate " +
                                "ORDER BY g.startTime DESC",
                        GroundRental.class)
                .setParameter("user", user)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<GroundRentalHistory> findAllByUserAndTime(User user, LocalDateTime dateTime) {
        return em.createQuery(
                        "select new pg.provingground.dto.history.GroundRentalHistory(g.groundRentalId, g.ground.name, g.startTime) " +
                                "from GroundRental g " +
                                "where g.user = :user " +
                                "and g.startTime = :dateTime", GroundRentalHistory.class)
                .setParameter("user", user)
                .setParameter("dateTime", dateTime)
                .getResultList();
    }

    /** 해당 날짜 예약건들을 <시간별, 예약건수> 로 반환 */
    public Map<LocalTime, Long> findAvailableTimesCount(Long groundId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        List<Object[]> results = em.createQuery(
                        "SELECT g.startTime, COUNT(g) " +
                                "FROM GroundRental g " +
                                "WHERE g.startTime BETWEEN :start AND :end AND g.ground.groundId = :groundId " +
                                "AND g.canceled = 'N' " +
                                "GROUP BY g.startTime", Object[].class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .setParameter("groundId", groundId)
                .getResultList();

        Map<LocalTime, Long> countByTime = new HashMap<>();
        for (Object[] result : results) {
            LocalTime time = ((LocalDateTime) result[0]).toLocalTime();
            Long count = (Long) result[1];
            countByTime.put(time, count);
        }

        return countByTime;
    }

    /** 시간대별 시험장 예약현황 반환. */
    public List<LocalDateTime> getGroundRentalStatus(Long groundId) {
        // 예약 가능일: 예약 시점 다음날 ~ +30일
        LocalDateTime start = LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(30);
        return em.createQuery(
                        "SELECT c.startTime " +
                                "FROM GroundRental c " +
                                "WHERE c.startTime > :start and c.startTime < :end " +
                                "AND c.canceled  = 'N' and c.ground = :groundId ",
                        LocalDateTime.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("groundId", groundId)
                .getResultList();
    }

    /** [관리자] 대여 조건 검색 구현 */
    public List<GroundRental> findByCriteria(GroundRentalSearchForm searchForm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GroundRental> cq = cb.createQuery(GroundRental.class);
        Root<GroundRental> groundRental = cq.from(GroundRental.class);

        // 연관된 엔티티 로드를 위한 fetch join
        Fetch<GroundRental, Ground> groundFetch = groundRental.fetch("ground", JoinType.LEFT);
        Fetch<GroundRental, User> userFetch = groundRental.fetch("user", JoinType.LEFT);

        // 조건을 설정하기 위한 Join
        Join<GroundRental, Ground> groundJoin = groundRental.join("ground", JoinType.LEFT);
        Join<GroundRental, User> userJoin = groundRental.join("user", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        // 검색 조건: groundRentalId
        if (searchForm.getGroundRentalId() != null) {
            predicates.add(cb.equal(groundRental.get("groundRentalId"), searchForm.getGroundRentalId()));
        }

        // 검색 조건: groundName
        if (searchForm.getGroundName() != null && !searchForm.getGroundName().isEmpty()) {
            predicates.add(cb.like(groundJoin.get("name"), "%" + searchForm.getGroundName() + "%"));
        }

        // 검색 조건: startDate와 endDate
        if (searchForm.getStartDate() != null && !searchForm.getStartDate().isEmpty() &&
                searchForm.getEndDate() != null && !searchForm.getEndDate().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(searchForm.getStartDate(), formatter);
            LocalDateTime endDate = LocalDateTime.parse(searchForm.getEndDate(), formatter);
            predicates.add(cb.between(groundRental.get("startTime"), startDate, endDate));
        }

        // 검색 조건: canceled
        if (searchForm.getCanceled() != null && !searchForm.getCanceled().isEmpty()) {
            predicates.add(cb.equal(groundRental.get("canceled"), searchForm.getCanceled()));
        }

        // 검색 조건: username
        if (searchForm.getUsername() != null && !searchForm.getUsername().isEmpty()) {
            predicates.add(cb.like(userJoin.get("username"), "%" + searchForm.getUsername() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<GroundRental> query = em.createQuery(cq);
        return query.getResultList();
    }

}