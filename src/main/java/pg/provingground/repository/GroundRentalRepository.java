package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pg.provingground.domain.*;

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

    /** 시간대별 시험장 예약현황 반환. */
    public List<LocalDateTime> getGroundRentalStatus(Long groundId) {
        // 예약 가능일: 예약 시점 다음날 ~ +30일
        LocalDateTime start = LocalDateTime.now().plusDays(1).with(LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(30);
        return em.createQuery(
                        "SELECT c.startTime " +
                                "FROM GroundRental c " +
                                "WHERE c.startTime > :start and c.startTime < :end " +
                                "AND c.returned  = 'N' and c.ground = :groundId ",
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
        String jpql = "select gr from GroundRental gr where gr.returned = 'N' and gr.ground = :groundId";
        return em.createQuery(jpql, GroundRental.class)
                .setParameter("groundId", groundId)
                .getResultList();
    }
}