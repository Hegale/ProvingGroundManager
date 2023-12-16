package pg.provingground.service;

import org.junit.jupiter.api.Test;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;
import pg.provingground.repository.CarRepository;
import pg.provingground.repository.CarTypeRepository;
import pg.provingground.repository.StationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarServiceTest {

    @Test
    void whenAmountIsInvalid_thenThrowException() {CarRepository carRepository = mock(CarRepository.class);
        CarTypeRepository carTypeRepository = mock(CarTypeRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        CarService carService = new CarService(carRepository, carTypeRepository, stationRepository);

        assertThrows(IllegalArgumentException.class, () -> {
            carService.validFuelAmount(1L, -1L);
        });
    }

    @Test
    void whenAmountIsValid_thenReturnCorrectValue() {
        // Mock 객체 생성
        CarRepository carRepository = mock(CarRepository.class);
        CarTypeRepository carTypeRepository = mock(CarTypeRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);
        Car mockCar = mock(Car.class);
        CarType mockType = mock(CarType.class);

        // CarService 생성
        CarService carService = new CarService(carRepository, carTypeRepository, stationRepository);

        // Mock 동작 정의
        when(carRepository.findOne(1L)).thenReturn(mockCar);
        when(mockCar.getType()).thenReturn(mockType);
        when(mockCar.getFuel()).thenReturn(5L); // 현재 연료량 5L
        when(mockType.getFuelCapacity()).thenReturn(15L); // 연료 탱크 용량 15L

        // 예상 결과는 10L (연료 탱크 용량 15L - 현재 연료량 5L)
        Long expected = 10L;
        Long actual = carService.validFuelAmount(1L, 2L);

        assertEquals(expected, actual);
    }

}