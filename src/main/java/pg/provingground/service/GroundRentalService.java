package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;
import pg.provingground.repository.GroundRentalRepository;
import pg.provingground.repository.GroundRepository;
import pg.provingground.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroundRentalService {

    private final GroundRentalRepository groundRentalRepository;
    private final UserRepository userRepository;
    private final GroundRepository groundRepository;

    /** 시험장 예약 */
    @Transactional
    public Long rental(Long userId, Long groundId, LocalDateTime time) {

        User user = userRepository.findOne(userId);
        Ground ground = groundRepository.findOne(groundId);

        // TODO: 실제 생성하기 직전, 그 사이에 추가로 예약이 진행된 것은 없는지 확인해야 함.
        // 사용자가 페이지를 띄워놓고 오랜 시간이 지났는데, 그 사이 누군가 예약을 채갔을 수도 있으므로
        GroundRental groundRental = GroundRental.createGroundRental(user, ground, time);

        groundRentalRepository.save(groundRental);
        return groundRental.getGroundRentalId();
    }

    /** 대여 취소 및 차량 반납 */
    // TODO: 오늘 이후의 예약은 반납, 그 이전은 취소. 그 구분은 컨트롤러에서
    @Transactional
    public void cancelRental(Long carRentalId) {
        GroundRental rental = groundRentalRepository.findOne(carRentalId);
        rental.cancel();
    }

    /** 전체 대여 내역 검색 */
    public List<GroundRental> findRentals() {
        return groundRentalRepository.findAll();
    }

}