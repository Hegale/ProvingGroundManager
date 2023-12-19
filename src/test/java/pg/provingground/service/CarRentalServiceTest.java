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
import pg.provingground.exception.NoAvailableCarException;
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


    /*
    @BeforeEach
    void setUp() {
        Long carRentalId = 1L;
        when(carRentalRepository.findOne(carRentalId)).thenReturn(carRental);
    }

     */

    @Test
    public void 차량대여_성공() {
        //given
        Long userId = 1L;
        Long carTypeId = 1L;
        LocalDateTime time = LocalDateTime.now();

        when(carRepository.findByCarType(anyLong())).thenReturn(List.of(car));
        when(carRepository.findOne(anyLong())).thenReturn(car);

        // save의 매개변수가 되는 객체를 캡처
        ArgumentCaptor<CarRental> captor = ArgumentCaptor.forClass(CarRental.class);
        when(carRentalRepository.save(captor.capture())).thenAnswer(invocation -> {
            CarRental captured = captor.getValue();
            captured.setCarRentalId(1L); // 여기에서 ID를 설정합니다.
            return captured;
        });

        //when
        Long carRentalId = carRentalService.rental(userId, carTypeId, time);

        //then
        verify(carRentalRepository).save(any(CarRental.class));
        assertNotNull(carRentalId);
        assertEquals(1L, carRentalId);
    }

    @Test
    public void 차량대여_실패() throws Exception{
        //given
        Long userId = 1L;
        Long carTypeId = 1L;
        LocalDateTime time = LocalDateTime.now();

        // 대여하고자 하는 차량이 반납되지 않은 차량이라고 가정
        when(carRentalRepository.findNotReturned(anyLong(), any())).thenReturn(List.of(car));
        when(carRepository.findByCarType(anyLong())).thenReturn(List.of(car));

        //when
        //then
        assertThrows(NoAvailableCarException.class, () -> {
            carRentalService.rental(userId, carTypeId, time);
        });
    }

    @Test
    public void 대여_취소() {
        //given
        Long carRentalId = 1L;
        when(carRentalRepository.findOne(carRentalId)).thenReturn(carRental);

        //when
        carRentalService.cancelRental(carRentalId);

        //then
        verify(carRentalRepository).findOne(carRentalId);
        verify(carRental).cancel();
    }



}