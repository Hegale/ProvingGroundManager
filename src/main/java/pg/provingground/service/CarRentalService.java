package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.CarType;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.CarRentalDto;
import pg.provingground.dto.admin.CarRentalSearchForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.exception.NoAvailableCarException;
import pg.provingground.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public Long rental(Long userId, Long carTypeId, LocalDateTime time) {

        User user = userService.getLoginUserById(userId);
        Long carId = getSchedulableCar(carTypeId, time);
        Car car = carRepository.findOne(carId);

        CarRental carRental = CarRental.createCarRental(user, car, time);

        carRentalRepository.save(carRental);
        return carRental.getCarRentalId();
    }

    /** 해당 차종의 차량 중, 해당 시간대에 예약 가능한 차량 하나 반환 */
    public Long getSchedulableCar(Long carTypeId, LocalDateTime time) {
        List<Car> unavailableCars = carRentalRepository.findNotReturned(carTypeId, time);
        List<Car> cars = carRepository.findByCarType(carTypeId);
        for (Car car : cars) {
            if (!unavailableCars.contains(car)) {
                return car.getCarId();
            }
        }
        // 모든 차량이 예약 불가능하면
        throw new NoAvailableCarException("해당 시간대에 예약 가능한 차량이 없습니다!");
    }

    /** 대여 취소 및 차량 반납 */
    @Transactional
    public void cancelRental(Long carRentalId) {
        CarRental rental = carRentalRepository.findOne(carRentalId);
        rental.cancel();
    }

    /** [관리자] 검색 조건에 따른 차량 대여 내역 반환 */
    public List<CarRentalDto> getRentalsByConditions(CarRentalSearchForm carRentalSearchForm) {

        // 날짜 조건이 입력된 경우, 해당 조건 추가
        if (carRentalSearchForm.getStartDate() != null && carRentalSearchForm.getEndDate() != null) {
            LocalDateTime start = DateTimeUtils.convertToStartOfDay(carRentalSearchForm.getStartDate());
            LocalDateTime end = DateTimeUtils.convertToEndOfDay(carRentalSearchForm.getEndDate());
            carRentalSearchForm.setStartDateTime(start);
            carRentalSearchForm.setEndDateTime(end);
        }

        return carRentalRepository.searchCarRentals(carRentalSearchForm);
    }

    /** [관리자] 모든 차량 대여 내역 반환 */
    public List<CarRentalHistory> getAllRentals() {
        return carRentalRepository.findAll().stream()
                .map(carRental -> new CarRentalHistory(carRental))  // CarRental 객체를 CarRentalHistory 객체로 변환
                .collect(Collectors.toList());
    }

    /** 특정 차종과 날짜를 받아 대여 가능한 시간대를 리스트로 반환 */
    public List<String> getAvailableTimes(Long carTypeId, String selectedDate) {

        LocalDate date = LocalDate.parse(selectedDate);
        // 해당 차종의 총 차량 대수
        long carCount = carRepository.countCarsPerCarType(carTypeId);
        // date 날짜의 carTypeId 차량들의 대여 현황
        Map<LocalTime, Long> countByTime = carRentalRepository.findRentalCountByTime(carTypeId, date);

        List<String> availableTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        int intervalHours = 2;

        // 10:00부터 18:00까지 두 시간 간격으로 검색
        for (LocalTime time = startTime; !time.isAfter(endTime); time = time.plusHours(intervalHours)) {
            if (countByTime.getOrDefault(time, 0L) < carCount) {
                availableTimes.add(String.format("%02d", time.getHour()));
            }
        }

        return availableTimes;
    }

    /** 한 유저의 차량 대여 내역을 dto로 변환하여 반환 */
    public List<CarRentalHistory> findRentalHistory(User user, DateSearchForm dateSearchForm) {
        List<CarRental> rentals;

        // 날짜를 선택하지 않았을 경우
        if (dateSearchForm.getStartDate() == null || dateSearchForm.getEndDate() == null) {
            rentals = carRentalRepository.findAllByUser(user);
        } else { // 날짜를 선택했을 경우
            // daterangepicker에서 받아온 결과를 LocalDate로 변환
            LocalDate start = LocalDate.parse(dateSearchForm.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate end = LocalDate.parse(dateSearchForm.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            rentals = carRentalRepository.findAllByUserAndTimeInterval(user, LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        }

        List<CarRentalHistory> history = rentals.stream()
                .map(CarRentalHistory::new)
                .collect(Collectors.toList());

        return history;
    }

    /** 요청을 보낸 유저가 해당 차량 대여 기록의 주인인지 확인 */
    public boolean isOwnerMatched(Long carRentalId, User user) {
        CarRental carRental = carRentalRepository.findOne(carRentalId);
        return carRental.getUser().equals(user);
    }

    public CarType findType(Long carTypeId) {
        return carTypeRepository.findOne(carTypeId);
    }

}
