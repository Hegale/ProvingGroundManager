package pg.provingground.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import pg.provingground.domain.*;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;
import pg.provingground.dto.admin.StationForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.RefuelHistory;
import pg.provingground.repository.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RefuelServiceTest {

    @Mock
    private RefuelRepository refuelRepository;
    @Mock
    private UserService userService;
    @Mock
    private CarRepositoryImpl carRepository;
    @Mock
    private StationRepositoryImpl stationRepository;

    @InjectMocks
    private RefuelService refuelService;


    private User user;
    private Car car;
    private CarType carType;
    private Station station;
    private Refuel refuel;

    private FuelType fuelType;
    private LocalDateTime time;


    @BeforeEach
    void setUp() {
        time = LocalDateTime.now();

        user = mock(User.class);
        refuel = mock(Refuel.class);
        car = mock(Car.class);
        carType = mock(CarType.class);
        station = mock(Station.class);
        fuelType = FuelType.GASOLINE;
        //when(refuel.getRefuelingId()).thenReturn(1L);
    }

    @Test
    void 주유_성공() {
        //given
        when(car.getType()).thenReturn(carType);
        when(carRepository.findOne(anyLong())).thenReturn(car);
        when(stationRepository.findOne(anyLong())).thenReturn(station);
        when(station.getFuelType()).thenReturn(fuelType);
        when(carType.getEngine()).thenReturn("가솔린");
        when(userService.getLoginUserByUsername(any())).thenReturn(user);

        //when
        Long refuelId = refuelService.refuel("username", 1L, 1L, time, 50L);

        //then
        // save가 호출됐다면 제대로 저장된 것
        verify(carRepository).findOne(anyLong());
        verify(stationRepository).findOne(anyLong());
        verify(refuelRepository).save(any(Refuel.class));
    }

    @Test
    void 주유_실패() {
        //given
        when(car.getType()).thenReturn(carType);
        when(carRepository.findOne(anyLong())).thenReturn(car);
        when(stationRepository.findOne(anyLong())).thenReturn(station);
        when(station.getFuelType()).thenReturn(fuelType);
        // 주유구 연료타입과 차량 엔진 유형이 다른 경우
        when(carType.getEngine()).thenReturn("디젤");
        when(userService.getLoginUserByUsername(any())).thenReturn(user);

        //when
        //then
        // 예외를 발령해야 한다
        assertThrows(IllegalArgumentException.class, () ->
                refuelService.refuel("username", 1L, 1L, time, 50L));
    }

    @Test
    void 유저_주유내역_반환() {
        //given
        Long userId = 1L;
        when(refuelRepository.findUserRefuel(userId)).thenReturn(List.of(refuel));

        //when
        List<Refuel> result = refuelService.findUserRefuel(userId);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(refuelRepository).findUserRefuel(userId);
    }

    @Test
    void 관리자_조건으로_검색() {
        //given
        RefuelSearchForm searchForm = new RefuelSearchForm();
        searchForm.setStartDate("2023-01-01");
        searchForm.setEndDate("2023-01-31");

        when(refuelRepository.searchRefuels(searchForm)).thenReturn(List.of(new RefuelDto()));

        //when
        List<RefuelDto> result = refuelService.searchRefuelsByConditions(searchForm);

        //then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(refuelRepository).searchRefuels(searchForm);
    }

    @Test
    void addStation_WithValidInput_SuccessfullyAdded() {
        //given
        StationForm stationForm = new StationForm();
        stationForm.setName("New Station");
        stationForm.setFuelType(FuelType.GASOLINE);

        //when
        refuelService.addStation(stationForm);

        //then
        // 시험장 추가 시 save가 호출되어야 정상작동한 것으로 간주
        verify(stationRepository).save(any(Station.class));
    }

}
