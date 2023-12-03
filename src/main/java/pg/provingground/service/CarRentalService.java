package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.CarRepository;
import pg.provingground.repository.CarTypeRepository;
import pg.provingground.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarRentalService {

    private final CarRentalRepository carRentalRepository;
    private final CarTypeRepository carTypeRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    /** 차량 대여 */
    @Transactional
    public Long rental(Long userId, Long carId, LocalDateTime time) {

        User user = userRepository.findOne(userId);
        Car car = carRepository.findOne(carId);

        CarRental carRental = CarRental.createCarRental(user, car, time);

        carRentalRepository.save(carRental);
        return carRental.getCarRentalId();
    }

    /** 대여 취소 및 차량 반납 */
    // TODO: 오늘 이후의 예약은 반납, 그 이전은 취소. 그 구분은 컨트롤러에서
    @Transactional
    public void cancelRental(Long carRentalId) {
        CarRental rental = carRentalRepository.findOne(carRentalId);
        rental.cancel();
    }

    /** 한 유저의 차량 대여 내역을 dto로 변환하여 반환 */
    public List<CarRentalHistory> findRentalHistory(User user) {
        List<CarRental> rentals = carRentalRepository.findAllByUser(user);
        List<CarRentalHistory> history = rentals.stream()
                .map(CarRentalHistory::new)
                .collect(Collectors.toList());
        return history;
    }

    /** [관리자 기능] 전체 대여 내역 검색 */
    public List<CarRental> findRentals() {
        return carRentalRepository.findAll();
    }

}
