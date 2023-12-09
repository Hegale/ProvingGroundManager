package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.*;
import pg.provingground.dto.GroundRentalHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public List<GroundRentalHistory> findAllByUserAndTime(User user, LocalDate date) {
        return em.createQuery(
                        "select new pg.provingground.dto.GroundRentalHistory(g.groundRentalId, g.ground.name, g.startTime) " +
                                "from GroundRental g " +
                                "where g.user = :user " +
                                "and FUNCTION('DATE', g.startTime) = :date", GroundRentalHistory.class)
                .setParameter("user", user)
                .setParameter("date", date)
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

    /**
     * 인자로 받은 차량 중 반납되지 않은 대여기록 반환. TODO: 삭제
     */
    public List<GroundRental> findNotReturned(Long groundId) {
        String jpql = "select gr from GroundRental gr where gr.canceled = 'N' and gr.ground = :groundId";
        return em.createQuery(jpql, GroundRental.class)
                .setParameter("groundId", groundId)
                .getResultList();
    }
}