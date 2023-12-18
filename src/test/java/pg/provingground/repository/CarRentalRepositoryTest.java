package pg.provingground.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DataJpaTest
@TestConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRentalRepositoryTest {

    @Autowired private CarRentalRepositoryImpl carRentalRepository;

    private static CarRental carRental;
    private User user;
    private Car car;

    @BeforeEach
    void setup() {
        user = mock(User.class);
        car = mock(Car.class);
        carRental = CarRental.createCarRental(user, car, LocalDateTime.now());
    }

    @Test
    public void 시간_간격으로_유저_대여기록_검색() throws Exception {
        //given
        // 1분 간격으로 time interval 생성
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(1L);
        LocalDateTime endTime = LocalDateTime.now().plusMinutes(1L);
        carRentalRepository.save(carRental);

        //when
        List<CarRental> result = carRentalRepository.findAllByUserAndTimeInterval(user, startTime, endTime);

        //then
        // 생성했던 carRental과 timeInterval의 결과가 일치해야 함
        assertEquals(result.get(0), carRental);
    }

}