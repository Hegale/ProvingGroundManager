package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.dto.admin.GroundRentalDto;
import pg.provingground.dto.admin.GroundRentalSearchForm;
import pg.provingground.repository.GroundRentalRepository;
import pg.provingground.repository.GroundRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroundService {

    private final GroundRepository groundRepository;
    private final GroundRentalRepository groundRentalRepository;

    @Transactional
    public void saveGround(Ground ground) {
        groundRepository.save(ground);
    }

    @Transactional
    public void deleteGround(Long groundId) {
        Ground ground = groundRepository.findOne(groundId);
        groundRepository.delete(ground);
    }

    /** [관리자] 조건에 따라 시험장 예약 검색 */
    public List<GroundRentalDto> searchGroundRentalsByConditions(GroundRentalSearchForm searchForm) {
        List<GroundRental> groundRentals = groundRentalRepository.findByCriteria(searchForm);
        return groundRentals.stream()
                .map(GroundRentalDto::new)
                .collect(Collectors.toList());
    }

    // TODO: 시험장 수정 메소드 구현
    // TODO: 시험장 삭제 메소드 구현

    public List<Ground> findItems() {
        return groundRepository.findAll();
    }

    public Ground findOne(Long groundId) {
        return groundRepository.findOne(groundId);
    }
}
