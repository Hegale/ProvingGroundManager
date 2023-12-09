package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.CarType;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.exception.NoAvailableCarException;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.CarRepository;
import pg.provingground.repository.CarTypeRepository;
import pg.provingground.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarRentalService {

    private final CarRentalRepository carRentalRepository;
    private final CarTypeRepository carTypeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CarRepository carRepository;

    /** 차량 대여 */
    @Transactional
    public Long rental(Long userId, Long carId, LocalDateTime time) {

        User user = userService.getLoginUserById(userId);
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

    /** 해당 차종의 차량 중, 해당 시간대에 예약 가능한 차량 하나 반환 */
    public Long getSchedulableCar(Long carTypeId, LocalDateTime time) {
        List<Car> unavailableCars = carRentalRepository.findUnavailableCars(carTypeId, time);
        List<Car> cars = carRepository.findByCarType(carTypeId);
        for (Car car : cars) {
            if (!unavailableCars.contains(car)) {
                return car.getCarId();
            }
        }
        // 모든 차량이 예약 불가능하면
        throw new NoAvailableCarException("해당 시간대에 예약 가능한 차량이 없습니다!");
    }

    /** 차량 대여가 불가능한 시간대 구해서 반환*/
    public List<LocalDateTime> getUnAvailableTimes(Long carTypeId) {
        Map<LocalDateTime, Integer> rentedCarsPerTimeSlot = carRentalRepository.countRentedCarsPerTimeSlot(carTypeId);
        long carCount = carRepository.countCarsPerCarType(carTypeId);
        List<LocalDateTime> unavailableTimes = new ArrayList<>();

        for (LocalDateTime time : rentedCarsPerTimeSlot.keySet()) {
            // 해당 시간대에 예약되지 않은 차량이 남아 있다면?
            if (rentedCarsPerTimeSlot.get(time) != carCount) {
                unavailableTimes.add(time);
            }
        }
        return unavailableTimes;
    }

    /** 특정 차종의 대여 현황을 통해 대여 가능한 시간대를 폼으로 변경해 반환 */
    public List<AvailableTimeForm> getAvailableTimeForms(Long carTypeId) {
        List<AvailableTimeForm> availableTimeForms = new ArrayList<>();
        List<LocalDateTime> unavailableTimes = getUnAvailableTimes(carTypeId);

        for (int i = 1; i <= 30; ++i) {
            LocalDateTime date = LocalDateTime.now().plusDays(i).with(LocalTime.of(10, 0));
            AvailableTimeForm timeForm = new AvailableTimeForm(date);

            // 2시간 간격으로 6번 반복 (10:00, 12:00, 14:00, 16:00, 18:00)
            for (int j = 0; j < 5; j++) {
                // 대여 불가능한 시간의 경우, 폼에 추가하지 않는다
                if (!unavailableTimes.contains(date.plusHours(2 * j))) {
                    timeForm.addTimes(date.plusHours(2 * j).getHour());
                }
            }
            availableTimeForms.add(timeForm);
        }
        return availableTimeForms; // 해당 차종을 대여할 수 없는 시간대 반환
    }

    /** 한 유저의 차량 대여 내역을 dto로 변환하여 반환 */
    public List<CarRentalHistory> findRentalHistory(User user) {
        List<CarRental> rentals = carRentalRepository.findAllByUser(user);
        List<CarRentalHistory> history = rentals.stream()
                .map(CarRentalHistory::new)
                .collect(Collectors.toList());
        return history;
    }

    /** 요청을 보낸 유저가 해당 차량 대여 기록의 주인인지 확인 */
    public boolean isOwnerMatched(Long carRentalId, User user) {
        return carRentalRepository.isUserMatched(carRentalId, user.getUserId());
    }

    public CarType findType(Long carTypeId) {
        return carTypeRepository.findOne(carTypeId);
    }

    /** [관리자 기능] 전체 대여 내역 검색 */
    public List<CarRental> findRentals() {
        return carRentalRepository.findAll();
    }

}
