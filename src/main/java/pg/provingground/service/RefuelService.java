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

        if (!isMatchFuel(car.getType().getEngine(), station.getFuelType())) {
            throw new IllegalArgumentException("주유구의 연료 타입이 차량 엔진과 일치하지 않습니다!");
        }
        // 새 주유 기록을 저장
        Refuel refuel = Refuel.createRefuel(user, car, station, time, amount);

        // 차량에 잔여연료 업데이트, 더티체킹
        car.fueling(amount);

        refuelRepository.save(refuel);
        return refuel.getRefuelingId();
    }

    private boolean isMatchFuel(String engine, FuelType fuelType) {
        if (engine.equals("하이브리드")) {
            engine = "가솔린";
        }
        if (fuelType.getDisplayName().equals(engine)) {
            return true;
        }
        return false;
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
        // 날짜 조건이 입력된 경우, 해당 조건 추가
        if (searchForm.getStartDate() != null && searchForm.getEndDate() != null) {
            LocalDateTime start = DateTimeUtils.convertToStartOfDay(searchForm.getStartDate());
            LocalDateTime end = DateTimeUtils.convertToEndOfDay(searchForm.getEndDate());
            searchForm.setStartDateTime(start);
            searchForm.setEndDateTime(end);
        }
        // 대여번호 조건이 입력된 경우, 이를 숫자로 변환
        if (searchForm.getRefuelIdString() != null && !searchForm.getRefuelIdString().isEmpty()) {
            searchForm.setRefuelId(Long.parseLong(searchForm.getRefuelIdString()));
        }

        return refuelRepository.searchRefuels(searchForm);
    }

    /** [관리자] 주유구 추가 */
    @Transactional
    public void addStation(StationForm stationForm) {
        Station station = Station.createStation(stationForm.getName(), stationForm.getFuelType());
        stationRepository.save(station); // TODO: 스테이션 이름 중복 확인
    }

}