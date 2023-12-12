package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;
import pg.provingground.dto.admin.CarDto;
import pg.provingground.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    /** 차량번호를 통한 차량 검색. 입력이 아예 들어오지 않을 경우 모든 차량을 반환. */
    public List<CarDto> findByCarNumber(String number) {
        List<Car> cars;
        if (number == null || number.isEmpty()) {
            cars = carRepository.findAll();
        } else {
            cars = carRepository.findByNumber(number);
        }
        return cars.stream()
                .map(car -> new CarDto(
                        car.getCarId(),
                        car.getNumber(),
                        car.getFuel(),
                        car.getType().getName(),
                        car.getType().getType(),
                        car.getType().getEngine()))
                .collect(Collectors.toList());
    }

    /** 차종을 통해 차량 검색. */
    public List<CarDto> findByCarType(List<CarType> carTypes) {
        return carRepository.findByCarTypes(carTypes)
                .stream()
                .map(car -> new CarDto(
                        car.getCarId(),
                        car.getNumber(),
                        car.getFuel(),
                        car.getType().getName(),
                        car.getType().getType(),
                        car.getType().getEngine()))
                .collect(Collectors.toList());
    }

    /** 선택 차량에 적합한 주유구인지 확인 */
    public boolean isValidStation(Long carId, Long stationId) {
        // TODO: station에 연료 타입 추가하여 car의 연료 타입과 일치하는지 확인
        return true;
    }

    /** 입력된 연료량을 유효한 값으로 변경하여 반환 */
    public Long validFuelAmount(Long carId, Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("유효하지 않은 주유량입니다.");
        }
        Car car = carRepository.findOne(carId);
        // 연료 탱크의 남은 공간
        Long leftCapacity = car.getType().getFuelCapacity() - car.getFuel();
        return Math.min(amount * 1000, leftCapacity) ;
    }

    /** 차량을 삭제 */
    @Transactional
    public void deleteCar(Long carId) {
        Car car = carRepository.findOne(carId);
        carRepository.delete(car);
    }

}
