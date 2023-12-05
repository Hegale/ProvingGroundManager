package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;
import pg.provingground.dto.CarDto;
import pg.provingground.repository.CarRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    /** 차량번호를 통한 차량 검색. 입력이 아예 들어오지 않을 경우 모든 차량을 반환. */
    public List<CarDto> findByCarNumber(String number) {
        if (number == null || number.isEmpty()) {
            return carRepository.findAllDto();
        }
        return carRepository.findByNumber(number);
    }

    /** 차종을 통해 차량 검색. */
    public List<CarDto> findByCarType(List<CarType> carTypes) {
        return carRepository.findByCarTypes(carTypes);
    }
}
