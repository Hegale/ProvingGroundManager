package pg.provingground.repository;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.admin.TestSearchForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    public void edit(Test test, TestDto testDto);

    public Test findOne(Long testId);
    public boolean findByGroundRental(Long groundRentalId);

    public List<TestDto> findAllDto();

    public List<Test> findAllByUser(User user);

    public List<Test> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate);

    public List<TestDto> searchTests(TestSearchForm searchForm);
}
