package pg.provingground.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pg.provingground.domain.*;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.CarRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRentalServiceTest {
    @Mock
    private CarRentalRepository carRentalRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private UserService userService;
    @Mock
    private Car car;
    @Mock
    private User user;
    @Mock
    private CarRental carRental;
    private LocalDateTime time = LocalDateTime.now();

    @InjectMocks
    private CarRentalService carRentalService;

    @BeforeEach
    void setUp() {
        when(userService.getLoginUserById(anyLong())).thenReturn(user);
        when(car.getCarId()).thenReturn(1L);
        when(carRepository.findByCarType(anyLong())).thenReturn(List.of(car));
        when(carRepository.findOne(anyLong())).thenReturn(car);
        when(carRentalRepository.findNotReturned(anyLong(), any())).thenReturn(List.of());
    }

    @Test
    public void rental_예약가능한차량이있을때_대여성공() {
        Long userId = 1L;
        Long carTypeId = 1L;
        LocalDateTime time = LocalDateTime.now();

        ArgumentCaptor<CarRental> captor = ArgumentCaptor.forClass(CarRental.class);
        when(carRentalRepository.save(captor.capture())).thenAnswer(invocation -> {
            CarRental captured = captor.getValue();
            captured.setCarRentalId(1L); // 여기에서 ID를 설정합니다.
            return captured;
        });

        Long carRentalId = carRentalService.rental(userId, carTypeId, time);

        verify(carRentalRepository).save(any(CarRental.class));
        assertNotNull(carRentalId);
        assertEquals(1L, carRentalId);
    }
}