package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.dto.GroundRentalHistory;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.GroundRentalRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final GroundRentalRepository groundRentalRepository;
    private final CarRentalRepository carRentalRepository;

    /** user가 time에 대여했던 시험장 기록들을 불러오기 */
    public List<GroundRentalHistory> getUsersGroundList(User user, LocalDate date) {
        if (date == null) {
            return null;
        }
        return groundRentalRepository.findAllByUserAndTime(user, date);
    }

    /** user가 time에 대여했던 차량 기록들을 볼러오기 */
    public List<CarRentalHistory> getUsersCarList(User user, LocalDate date) {
        if (date == null) {
            return null;
        }
        return carRentalRepository.findAllByUserAndTime(user, date);
    }


}
