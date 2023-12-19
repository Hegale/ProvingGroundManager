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
import pg.provingground.dto.admin.CarRentalDto;
import pg.provingground.dto.admin.CarRentalSearchForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.exception.NoAvailableCarException;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.CarRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

    @Test
    public void 날짜_및_조건으로_검색() {
        // given
        CarRentalSearchForm searchForm = new CarRentalSearchForm();
        searchForm.setStartDate("2023-01-01");
        searchForm.setEndDate("2023-01-31");

        // repository를 통한 검색 결과를 가정
        List<CarRentalDto> expectedRentals = List.of(new CarRentalDto(), new CarRentalDto());
        when(carRentalRepository.searchCarRentals(any(CarRentalSearchForm.class))).thenReturn(expectedRentals);

        // when
        List<CarRentalDto> actualRentals = carRentalService.getRentalsByConditions(searchForm);

        // then
        // 검색 결과가 존재하는지, 예상결과와 일치하는지 확인
        assertNotNull(actualRentals);
        assertEquals(expectedRentals.size(), actualRentals.size());
        assertEquals(expectedRentals, actualRentals);
    }

    @Test
    public void 대여가능_시간대_반환() {
        // given
        // 3대 보유중인 차종의 selectedDate 일 대여가능 시간대 확인
        Long carTypeId = 1L;
        String selectedDate = "2024-01-01";
        long carCount = 3; // 예를 들어, 해당 차종의 총 차량 대수가 3대라고 가정

        Map<LocalTime, Long> countByTime = new HashMap<>();
        // 12:00과 14:00에 이미 대여가 예약된 상황
        countByTime.put(LocalTime.of(12, 0), 3L);
        countByTime.put(LocalTime.of(14, 0), 2L);

        // 해당 차종의 차량 대수 = carCount
        when(carRepository.countCarsPerCarType(carTypeId)).thenReturn(carCount);
        // 12시 3대, 14시 2대 예약된 상황 설정
        when(carRentalRepository.findRentalCountByTime(carTypeId, LocalDate.parse(selectedDate))).thenReturn(countByTime);

        // when
        // 해당 차종의 대여 가능 시간대를 구했을 때
        List<String> availableTimes = carRentalService.getAvailableTimes(carTypeId, selectedDate);

        // then
        assertNotNull(availableTimes);
        assertTrue(availableTimes.contains("10")); // 10:00은 가능한 시간대
        assertFalse(availableTimes.contains("12")); // 12:00은 이미 만석
        assertTrue(availableTimes.contains("16")); // 16:00은 가능한 시간대
    }

    @Test
    public void 유저_대여내역_반환_날짜미선택() {
        // given
        User user = new User();
        Car car = new Car();
        CarType carType = new CarType();
        carType.setName("Sample Car Type");
        car.setType(carType);

        CarRental rental = new CarRental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setReturned("Y");

        List<CarRental> rentals = List.of(rental);
        when(carRentalRepository.findAllByUser(user)).thenReturn(rentals);

        // when
        List<CarRentalHistory> history = carRentalService.findRentalHistory(user, new DateSearchForm());

        // then
        assertNotNull(history);
        assertFalse(history.isEmpty());
    }

    @Test
    public void 유저_대여내역_반환_날짜선택() {
        // given
        User user = new User();
        Car car = new Car();
        CarType carType = new CarType();
        carType.setName("Sample Car Type");
        car.setType(carType);

        DateSearchForm dateSearchForm = new DateSearchForm();
        dateSearchForm.setStartDate("2023-01-01");
        dateSearchForm.setEndDate("2023-01-31");

        CarRental rental = new CarRental();
        rental.setCar(car);
        rental.setUser(user);
        rental.setReturned("Y");

        List<CarRental> rentals = List.of(rental);
        when(carRentalRepository.findAllByUserAndTimeInterval(eq(user), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(rentals);

        // when
        List<CarRentalHistory> history = carRentalService.findRentalHistory(user, dateSearchForm);

        // then
        assertNotNull(history);
        assertFalse(history.isEmpty());

    }

    @Test
    public void 주인일치() {
        // given
        Long carRentalId = 1L;
        User user = new User();

        CarRental carRental = new CarRental();
        carRental.setUser(user);

        when(carRentalRepository.findOne(carRentalId)).thenReturn(carRental);

        // when
        boolean isMatched = carRentalService.isOwnerMatched(carRentalId, user);

        // then
        assertTrue(isMatched);
    }

    @Test
    public void 주인불일치() {
        // given
        Long carRentalId = 1L;
        User user = new User(); // 예를 들어, 테스트에 사용할 User 객체 생성
        User otherUser = new User(); // 다른 User 객체 생성

        CarRental carRental = new CarRental();
        carRental.setUser(otherUser); // CarRental 객체에 다른 User 객체 설정

        when(carRentalRepository.findOne(carRentalId)).thenReturn(carRental);

        // when
        boolean isMatched = carRentalService.isOwnerMatched(carRentalId, user);

        // then
        assertFalse(isMatched); // 주인이 불일치하는 경우
    }



}