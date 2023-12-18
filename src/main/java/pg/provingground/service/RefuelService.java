package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;
import pg.provingground.dto.admin.StationForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.RefuelHistory;
import pg.provingground.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefuelService {

    private final RefuelRepository refuelRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CarRepositoryImpl carRepository;
    private final StationRepositoryImpl stationRepository;

    /** 주유 */
    @Transactional
    public Long refuel(String username, Long carId, Long stationId, LocalDateTime time, Long amount) {
        // 참조 객체들 찾아오기
        User user = userService.getLoginUserByUsername(username);
        Car car = carRepository.findOne(carId);
        Station station = stationRepository.findOne(stationId);

        // 새 주유 기록을 저장
        Refuel refuel = Refuel.createRefuel(user, car, station, time, amount);

        // 차량에 잔여연료 업데이트, 더티체킹
        car.fueling(amount);

        refuelRepository.save(refuel);
        return refuel.getRefuelingId();
    }

    public List<Refuel> findUserRefuel(Long userId) {
        return refuelRepository.findUserRefuel(userId);
    }

    public List<RefuelHistory> findRefuelHistory(User user, DateSearchForm dateSearchForm) {
        List<Refuel> refuels;

        if (dateSearchForm.getStartDate() == null || dateSearchForm.getEndDate() == null) {
            refuels = refuelRepository.findAllByUser(user);
        } else { // 날짜를 선택했을 경우
            LocalDate start = LocalDate.parse(dateSearchForm.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate end = LocalDate.parse(dateSearchForm.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            refuels = refuelRepository.findAllByUserAndTimeInterval(user, LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        }

        return refuels.stream()
                .map(RefuelHistory::new)
                .collect(Collectors.toList());
    }

    /** [관리자] 조건에 따른 주유 내역 검색 */
    public List<RefuelDto> searchRefuelsByConditions(RefuelSearchForm searchForm) {
        return refuelRepository.searchRefuels(searchForm);
    }

    /** [관리자] 주유구 추가 */
    @Transactional
    public void addStation(StationForm stationForm) {
        Station station = Station.createStation(stationForm.getName(), stationForm.getFuelType());
        stationRepository.save(station); // TODO: 스테이션 이름 중복 확인
    }

}