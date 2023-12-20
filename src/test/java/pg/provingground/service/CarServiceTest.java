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
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;
import pg.provingground.dto.admin.CarDto;
import pg.provingground.dto.admin.CarForm;
import pg.provingground.repository.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarTypeRepository carTypeRepository;

    @InjectMocks
    private CarService carService;

    private String carNumber;
    private Long carTypeId;
    private Long carId;

    @BeforeEach
    void setup() {
        carNumber = "52가 4671";
        carTypeId = 1L;
        carId = 1L;
    }

    @Test
    void 차량번호로_검색() {
        //given
        Car mockCar = mock(Car.class);
        CarType mockCarType = mock(CarType.class);

        when(mockCar.getNumber()).thenReturn(carNumber);
        when(mockCar.getType()).thenReturn(mockCarType);
        when(mockCarType.getName()).thenReturn("Test Car Type");
        when(carRepository.findByNumber(carNumber)).thenReturn(Arrays.asList(mockCar));

        //when
        List<CarDto> result = carService.findByCarNumber(carNumber);

        //then
        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getNumber(), carNumber);
    }

    @Test
    void 입력_연료량_유효하게_반환() {
        //given
        String amountString = "50";
        Car car = new Car();
        CarType carType = new CarType();
        carType.setFuelCapacity(50L);
        car.setFuel(20L);
        car.setType(carType);
        when(carRepository.findOne(carId)).thenReturn(car);

        //when
        // 차량 연료탱크 용량 50, 이미 주유된 연료 20인 상황에서 "50"이라는 입력을 유효한 연료량으로 변환
        Long result = carService.validFuelAmount(carId, amountString);

        //then
        // 결과는 탱크의 잔여용량인 30이어야 한다.
        assertEquals(30L, result);
    }

    @Test
    void 입력_연료량_예외_발령() {
        //given
        String amountString = "-10";

        //when
        //then
        // 연료량이 음수로 입력되면 예외 발령
        assertThrows(IllegalArgumentException.class, () -> carService.validFuelAmount(carId, amountString));
    }

    @Test
    void 차량_삭제() {
        // given
        Car mockCar = new Car();
        when(carRepository.findOne(carId)).thenReturn(mockCar);

        // when
        carService.deleteCar(carId);

        // then
        // 차량 삭제 시 repository의 delete 메서드가 호출되었는지 확인
        verify(carRepository).findOne(carId);
        verify(carRepository).delete(mockCar);
    }

    @Test
    void 차량생성() {
        //given
        CarType mockCarType = new CarType();
        when(carRepository.isDuplicateCarNumber(carNumber)).thenReturn(false);
        when(carTypeRepository.findOne(carTypeId)).thenReturn(mockCarType);

        //when
        carService.createCar(carTypeId, carNumber);

        //then
        // 번호가 중복되지 않았다면 정상적으로 차량을 생성하여 저장해야 함
        verify(carRepository).isDuplicateCarNumber(carNumber);
        verify(carRepository).save(any());
    }

    @Test
    void 차량생성_차량번호_중복_예외발령() {
        //given
        // 중복된 차량번호일 때
        when(carRepository.isDuplicateCarNumber(carNumber)).thenReturn(true);

        //when
        //then
        // 차량등록에 실패해야 함
        assertThrows(IllegalArgumentException.class, () -> carService.createCar(carTypeId, carNumber));
    }

    @Test
    void 차량수정() {
        //given
        // 변경할 차량 속성들 설정
        String newCarNumber = "99가 9999";
        String newFuelAmount = "30";
        CarForm carForm = new CarForm();
        carForm.setNumber(newCarNumber);
        carForm.setFuelAmount(newFuelAmount);

        CarType carType = new CarType();
        carType.setFuelCapacity(30L);
        Car car = new Car();
        car.setType(carType);

        when(carRepository.findOne(carId)).thenReturn(car);

        //when
        carService.editCar(carId, carForm);

        //then
        verify(carRepository).findOne(carId);
        // 차량 속성이 지정한 대로 변경되었는지 확인
        assertEquals(car.getFuel(), Long.parseLong(newFuelAmount));
        assertEquals(car.getNumber(), newCarNumber);
    }

    @Test
    void whenAmountIsInvalid_thenThrowException() {
        CarRepository carRepository = mock(CarRepository.class);
        CarTypeRepository carTypeRepository = mock(CarTypeRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        CarService carService = new CarService(carRepository, carTypeRepository, stationRepository);

        assertThrows(IllegalArgumentException.class, () -> {
            carService.validFuelAmount(1L, "-1");
        });
    }

}