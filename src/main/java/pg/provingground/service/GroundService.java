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

        car_maximum = Integer.parseInt(groundForm.getCar_maximum());
        distance = Integer.parseInt(groundForm.getDistance());
        if (car_maximum <= 0 || distance <= 0) {
            throw new IllegalArgumentException("유효하지 않은 입력입니다.");
        }
        if (groundRepository.isDuplicateGroundName(groundForm.getName())) {
            throw new IllegalArgumentException("중복된 시험장 이름입니다.");
        }

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

    public List<Ground> findItems() {
        return groundRepository.findAll();
    }

    public Ground findOne(Long groundId) {
        return groundRepository.findOne(groundId);
    }
}
