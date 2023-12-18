package pg.provingground.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.CarRentalDto;
import pg.provingground.dto.admin.CarRentalSearchForm;
import pg.provingground.dto.history.CarRentalHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRentalRepositoryTest {

    @Autowired private CarRentalRepository carRentalRepository;

    private CarRental carRental;
    //@MockBean
    private User user;
    private CarType carType;
    //@MockBean
    private Car car;
    private LocalDateTime now;

    @BeforeEach
    void setup() {
        user = User.createUser("tester", "1234", "테스터", UserRole.USER, "010-1234-5678");
        carType = new CarType();
        car = Car.createCar(carType, "12가 4567");
        now = LocalDateTime.now().with(LocalTime.of(10, 0));
        carRental = CarRental.createCarRental(user, car, now);
    }

    @Test
    public void 시간_간격으로_유저_대여기록_검색_성공() throws Exception {
        //given
        // 1분 간격으로 time interval 생성
        LocalDateTime startTime = now.minusMinutes(1L);
        LocalDateTime endTime = now.plusMinutes(1L);
        carRentalRepository.save(carRental);

        //when
        List<CarRental> result = carRentalRepository.findAllByUserAndTimeInterval(user, startTime, endTime);

        //then
        // 생성했던 carRental과 timeInterval의 결과가 일치해야 함
        assertEquals(result.get(0), carRental);
    }

    @Test
    public void 시간_간격으로_유저_대여기록_검색_실패() throws Exception {
        //given
        // 1분 간격으로 time interval 생성
        LocalDateTime startTime = now.plusSeconds(1L);
        LocalDateTime endTime = now.plusSeconds(2L);
        carRentalRepository.save(carRental);

        //when
        List<CarRental> result = carRentalRepository.findAllByUserAndTimeInterval(user, startTime, endTime);

        //then
        // 생성했던 carRental과 timeInterval의 결과가 일치해야 함
        assertTrue(result.isEmpty());
    }

    @Test
    public void 시간으로_유저_대여기록_검색() throws Exception {
        //given
        carRentalRepository.save(carRental);

        //when
        List<CarRentalHistory> result = carRentalRepository.findAllByUserAndTime(user, now);

        //then
        // 특정 시간대와 일치하는 데이터 검색 가능해야 함
        assertEquals(result.get(0).getCarRentalId(), carRental.getCarRentalId());
    }

    @Test
    public void 특정차종_대여가능_시간대_확인() throws Exception {
        //given
        LocalDate date = LocalDate.now();
        carRentalRepository.save(carRental);

        //when
        Map<LocalTime, Long> times = carRentalRepository.findRentalCountByTime(carType.getCarTypeId(), date);

        //then
        // 10:00 예약이므로, 해당 시간대에 1이 반환되어야 함
        assertEquals(times.get(LocalTime.of(10, 0)), 1L);
    }

    @Test
    public void 미반납_차량_확인() throws Exception {
        //given
        carRentalRepository.save(carRental);

        //when
        // 해당 시간대에 미반납된 차량 찾기
        List<Car> car = carRentalRepository.findNotReturned(carType.getCarTypeId(), now);

        //then
        // 반납되지 않았으므로 결과에 포함되어야 함
        assertEquals(car.get(0), carRental.getCar());
    }

    @Test
    public void 여러_조건으로_검색() throws Exception {
        //given
        CarRentalSearchForm searchForm = new CarRentalSearchForm();
        searchForm.setUsername("tester");
        carRentalRepository.save(carRental);

        //when
        List<CarRentalDto> result = carRentalRepository.searchCarRentals(searchForm);

        //then
        assertFalse(result.isEmpty());

    }

}