package pg.provingground.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pg.provingground.domain.*;
import pg.provingground.repository.CarRentalRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRentalServiceTest {
    @Mock
    private CarRentalRepository carRentalRepository;
    @InjectMocks
    private CarRentalService carRentalService;
    private static CarRental carRental;
    private static User user;
    private static CarType carType;
    private static Car car;
    private static LocalDateTime time;

    @BeforeAll
    static void setup() {
        user = User.createUser("tester", "1234", "테스터", UserRole.USER, "010-1234-5678");
        carType = new CarType();
        car = Car.createCar(carType, "12가 4567");
        time = LocalDateTime.now().with(LocalTime.of(10, 0));
    }

    @Test
    public void 차량_대여() throws Exception {
        Long userId = 1L;
        Long carTypeId = 1L;
        LocalDateTime time = LocalDateTime.now();
        CarRental carRental = new CarRental(); // 예시 객체

        when(carRentalRepository.save(any(CarRental.class))).thenReturn(carRental);

        Long carRentalId = carRentalService.rental(userId, carTypeId, time);

        verify(carRentalRepository).save(any(CarRental.class));

        // 여기서 carRentalId 또는 다른 로직에 대한 검증을 진행할 수 있습니다.


    }

}