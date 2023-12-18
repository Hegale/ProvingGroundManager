package pg.provingground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.Test;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.CarRentalDto;
import pg.provingground.dto.admin.CarRentalSearchForm;
import pg.provingground.dto.history.CarRentalHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface CarRentalRepository extends JpaRepository<CarRental, Long> {
    public CarRental findOne(Long id);
    public List<CarRental> findByIds(List<Long> ids);
    public List<CarRental> findByTest(Test test);
    List<CarRental> findAllByUser(User user);
    List<CarRental> findAllByUserAndTimeInterval(User user, LocalDateTime startDate, LocalDateTime endDate);
    List<CarRentalHistory> findAllByUserAndTime(User user, LocalDateTime dateTime);
    //public boolean isUserMatched(Long carRentalId, Long userId);
    public Map<LocalTime, Long> findRentalCountByTime(Long carTypeId, LocalDate date);
    public List<Car> findNotReturned(Long carTypeId, LocalDateTime time);
    public List<CarRentalDto> searchCarRentals(CarRentalSearchForm searchForm);

}
