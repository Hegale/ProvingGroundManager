package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.GroundRentalHistory;
import pg.provingground.repository.GroundRentalRepository;
import pg.provingground.repository.GroundRepository;
import pg.provingground.repository.UserRepository;

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
    private final UserRepository userRepository;
    private final UserService userService;
    private final GroundRepository groundRepository;

    /** 시험장 예약 */
    @Transactional
    public Long rental(Long userId, Long groundId, LocalDateTime time) {

        User user = userService.getLoginUserById(userId);
        Ground ground = groundRepository.findOne(groundId);

        // TODO: 실제 생성하기 직전, 그 사이에 추가로 예약이 진행된 것은 없는지 확인해야 함.
        // 사용자가 페이지를 띄워놓고 오랜 시간이 지났는데, 그 사이 누군가 예약을 채갔을 수도 있으므로
        GroundRental groundRental = GroundRental.createGroundRental(user, ground, time);

        groundRentalRepository.save(groundRental);
        return groundRental.getGroundRentalId();
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

    /** 해당 시험장 대여가 불가능한 시간대 구해서 반환*/
    public List<LocalDateTime> getUnAvailableTimes(Long groundId) {
        return groundRentalRepository.getGroundRentalStatus(groundId);
    }

    /** 특정 차종과 날짜를 받아 대여 가능한 시간대를 리스트로 반환 */
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

    /** [관리자 기능] 전체 대여 내역 검색 */
    public List<GroundRental> findRentals() {
        return groundRentalRepository.findAll();
    }

}
