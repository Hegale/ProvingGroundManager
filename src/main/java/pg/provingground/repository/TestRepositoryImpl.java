package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.Test;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.admin.TestSearchForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestRepositoryImpl {

    private final EntityManager em;

    public void save(Test test) {
        em.persist(test);
    }

    public void delete(Test test) {
        if (test != null) {
            em.remove(test);
        }
    }

    public void edit(Test test, TestDto testDto) {
        test.edit(testDto);
    }

    public Test findOne(Long testId) {
        return em.find(Test.class, testId);
    }

    public List<TestDto> findAllDto() {
        return em.createQuery(
                "SELECT new pg.provingground.dto.admin.TestDto(" +
                        "t.testId, t.dateTime, t.type, t.partners, t.title, t.contents, t.groundRental.ground.name, t.user.username) " +
                        "FROM Test t " +
                        "ORDER BY t.dateTime", TestDto.class)
                .getResultList();
    }

    public List<Test> findAllByUser(User user) {
        return em.createQuery(
                "SELECT t " +
                        "FROM Test t " +
                        "WHERE t.user = :user", Test.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Test> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return em.createQuery("SELECT t " +
                                "FROM Test t " +
                                "WHERE t.user = :user AND t.dateTime BETWEEN :startDate AND :endDate " +
                                "ORDER BY t.dateTime DESC",
                        Test.class)
                .setParameter("user", user)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<TestDto> searchTests(TestSearchForm searchForm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TestDto> cq = cb.createQuery(TestDto.class);
        Root<Test> testRoot = cq.from(Test.class);

        // 필요한 조인 구성
        Join<Test, User> userJoin = testRoot.join("user", JoinType.LEFT);
        Join<Test, GroundRental> groundRentalJoin = testRoot.join("groundRental", JoinType.LEFT);
        Join<GroundRental, Ground> groundJoin = groundRentalJoin.join("ground", JoinType.LEFT);

        // SELECT 절 구성 (TestDto 생성자 필요)
        cq.select(cb.construct(
                TestDto.class,
                testRoot.get("testId"),
                testRoot.get("dateTime"),
                testRoot.get("type"),
                testRoot.get("partners"),
                testRoot.get("title"),
                groundJoin.get("name"),
                userJoin.get("username")
        ));

        // 검색 조건 구성
        List<Predicate> predicates = new ArrayList<>();
        if (searchForm.getTestId() != null) {
            predicates.add(cb.equal(testRoot.get("testId"), searchForm.getTestId()));
        }
        if (StringUtils.hasText(searchForm.getType())) {
            predicates.add(cb.equal(testRoot.get("type"), searchForm.getType()));
        }
        if (StringUtils.hasText(searchForm.getPartners())) {
            predicates.add(cb.like(testRoot.get("partners"), "%" + searchForm.getPartners() + "%"));
        }
        if (StringUtils.hasText(searchForm.getTitle())) {
            predicates.add(cb.like(testRoot.get("title"), "%" + searchForm.getTitle() + "%"));
        }
        if (StringUtils.hasText(searchForm.getGroundName())) {
            predicates.add(cb.like(groundJoin.get("name"), "%" + searchForm.getGroundName() + "%"));
        }
        if (StringUtils.hasText(searchForm.getUsername())) {
            predicates.add(cb.like(userJoin.get("username"), "%" + searchForm.getUsername() + "%"));
        }

        if (searchForm.getStartTime() != null && searchForm.getEndTime() != null) {
            predicates.add(cb.between(testRoot.get("dateTime"),
                    searchForm.getStartTime(),
                    searchForm.getEndTime()));
        } else if (searchForm.getStartTime() != null) {
            predicates.add(cb.greaterThanOrEqualTo(testRoot.get("dateTime"),
                    searchForm.getStartTime()));
        } else if (searchForm.getEndTime() != null) {
            predicates.add(cb.lessThanOrEqualTo(testRoot.get("dateTime"),
                    searchForm.getEndTime()));
        }


        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(cq).getResultList();
    }
}
