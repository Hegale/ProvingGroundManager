package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.dto.admin.GroundForm;
import pg.provingground.dto.admin.GroundRentalDto;
import pg.provingground.dto.admin.GroundRentalSearchForm;
import pg.provingground.repository.GroundRentalRepository;
import pg.provingground.repository.GroundRentalRepositoryImpl;
import pg.provingground.repository.GroundRepository;
import pg.provingground.repository.GroundRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroundService {

    private final GroundRepository groundRepository;
    private final GroundRentalRepository groundRentalRepository;

    @Transactional
    public void addGround(GroundForm groundForm) {
        int car_maximum;
        int distance;
        System.out.println("groundForm = |" + groundForm.getCar_maximum() + "|" + groundForm.getDistance());

        try {
            car_maximum = Integer.parseInt(groundForm.getCar_maximum());
            distance = Integer.parseInt(groundForm.getDistance());
            System.out.println("parse끝");
            if (car_maximum <= 0 || distance <= 0) {
                return ; // TODO: 적절한 예외처리 필요, 두 요소는 입력되지 않을 수 있다
            }
        } catch (NumberFormatException e) {
            return ;
        }

        System.out.println("뭐가문제가?");
        Ground ground = Ground.createGround(groundForm.getName(), groundForm.getDescription(), car_maximum, distance);
        groundRepository.save(ground);
    }

    @Transactional
    public void deleteGround(Long groundId) {
        Ground ground = groundRepository.findOne(groundId);
        groundRepository.delete(ground);
    }

    @Transactional
    public void editGround(Long groundId, GroundForm groundForm) {
        Ground ground = groundRepository.findOne(groundId);
        ground.edit(groundForm);
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

        List<GroundRental> groundRentals = groundRentalRepository.searchGroundRentals(searchForm);
        return groundRentals.stream()
                .map(GroundRentalDto::new)
                .collect(Collectors.toList());
    }

    public List<Ground> findItems() {
        return groundRepository.findAll();
    }

    public Ground findOne(Long groundId) {
        return groundRepository.findOne(groundId);
    }
}
