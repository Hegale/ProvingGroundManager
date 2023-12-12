package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.Refuel;
import pg.provingground.domain.Station;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;
import pg.provingground.repository.CarRepository;
import pg.provingground.repository.RefuelRepository;
import pg.provingground.repository.StationRepository;
import pg.provingground.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefuelService {

    private final RefuelRepository refuelRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CarRepository carRepository;
    private final StationRepository stationRepository;

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

    public List<Refuel> findAllRefuel() {
        return refuelRepository.findAll();
    }

    /** [관리자] 조건에 따른 주유 내역 검색 */
    public List<RefuelDto> searchRefuelsByConditions(RefuelSearchForm searchForm) {
        return refuelRepository.searchRefuels(searchForm);
    }

}