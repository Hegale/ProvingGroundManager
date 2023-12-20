package pg.provingground.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.JpaRepository;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;
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

public interface GroundRentalRepository extends JpaRepository<GroundRental, Long> {
    public GroundRental findOne(Long id);
    public List<GroundRental> findAllByUser(User user);

    public List<GroundRental> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate);
    public List<GroundRentalHistory> findAllByUserAndTime(User user, LocalDateTime dateTime);
    public boolean isRentalAble(Ground ground, LocalDateTime time);
    public Map<LocalTime, Long> findAvailableTimesCount(Long groundId, LocalDate date);
    public List<LocalDateTime> getGroundRentalStatus(Long groundId);
    public List<GroundRental> searchGroundRentals(GroundRentalSearchForm searchForm);
}
