package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.GroundRentalDto;
import pg.provingground.dto.admin.GroundRentalSearchForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.GroundRentalHistory;
import pg.provingground.exception.NoAvailableCarException;
import pg.provingground.exception.NoAvailableGroundException;
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
public class GroundRentalService {

    private final GroundRentalRepository groundRentalRepository;
    private final UserService userService;
    private final GroundRepository groundRepository;

    /** 시험장 예약 */
    @Transactional
    public Long rental(Long userId, Long groundId, LocalDateTime time) {

        User user = userService.getLoginUserById(userId);
        Ground ground = groundRepository.findOne(groundId);

        isSchedulableGround(ground, time);
        GroundRental groundRental = GroundRental.createGroundRental(user, ground, time);

        groundRentalRepository.save(groundRental);
        return groundRental.getGroundRentalId();
    }

    /** 해당 차종의 차량 중, 해당 시간대에 예약 가능한 차량 하나 반환 */
    public void isSchedulableGround(Ground ground, LocalDateTime time) {
        if (!groundRentalRepository.isRentalAble(ground, time)) {
            throw new NoAvailableGroundException("해당 시간대에 예약 가능한 시험장이 없습니다!");
        }
    }

    /** 대여 취소 및 차량 반납 */
    @Transactional
    public void cancelRental(Long carRentalId) {
        GroundRental rental = groundRentalRepository.findOne(carRentalId);
        rental.cancel();
    }

    /** 한 유저의 시험장 예약 내역을 dto로 변환하여 반환 */
    public List<GroundRentalHistory> findRentalHistory(User user, DateSearchForm dateSearchForm) {
        List<GroundRental> rentals;

        if (dateSearchForm.getStartDate() == null || dateSearchForm.getEndDate() == null) {
            rentals = groundRentalRepository.findAllByUser(user);
        } else { // 날짜를 선택했을 경우
            LocalDate start = LocalDate.parse(dateSearchForm.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate end = LocalDate.parse(dateSearchForm.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            rentals = groundRentalRepository.findAllByUserAndTimeInterval(user, LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        }

        List<GroundRentalHistory> history = rentals.stream()
                .map(GroundRentalHistory::new)
                .collect(Collectors.toList());
        return history;
    }

    /** 특정 시험장과 날짜를 받아 대여 가능한 시간대를 리스트로 반환 */
    public List<String> getAvailableTimes(Long groundId, String selectedDate) {

        LocalDate date = LocalDate.parse(selectedDate);
        // date 날짜의 carTypeId 차량들의 대여 현황
        Map<LocalTime, Long> countByTime = groundRentalRepository.findAvailableTimesCount(groundId, date);

        List<String> availableTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        int intervalHours = 2;

        // 10:00부터 18:00까지 두 시간 간격으로 검색
        for (LocalTime time = startTime; !time.isAfter(endTime); time = time.plusHours(intervalHours)) {
            if (countByTime.getOrDefault(time, 0L) == 0) {
                availableTimes.add(String.format("%02d", time.getHour()));
            }
        }

        return availableTimes;
    }

    /** [관리자] 조건에 따라 시험장 예약 검색 */
    public List<GroundRentalDto> searchGroundRentalsByConditions(GroundRentalSearchForm searchForm) {
        // 날짜 조건이 입력된 경우, 해당 조건 추가
        if (searchForm.getStartDate() != null && searchForm.getEndDate() != null) {
            LocalDateTime start = DateTimeUtils.convertToStartOfDay(searchForm.getStartDate());
            LocalDateTime end = DateTimeUtils.convertToEndOfDay(searchForm.getEndDate());
            searchForm.setStartDateTime(start);
            searchForm.setEndDateTime(end);
        }

        // 대여번호 조건이 입력된 경우, 이를 숫자로 변환
        if (searchForm.getGroundRentalIdString() != null && !searchForm.getGroundRentalIdString().isEmpty()) {
            searchForm.setGroundRentalId(Long.parseLong(searchForm.getGroundRentalIdString()));
        }

        List<GroundRental> groundRentals = groundRentalRepository.searchGroundRentals(searchForm);
        return groundRentals.stream()
                .map(GroundRentalDto::new)
                .collect(Collectors.toList());
    }

    /** [관리자 기능] 전체 대여 내역 검색 */
    public List<GroundRental> findRentals() {
        return groundRentalRepository.findAll();
    }

}
