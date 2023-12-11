package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.*;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.dto.GroundRentalHistory;
import pg.provingground.dto.TestForm;
import pg.provingground.dto.TestHistory;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.GroundRentalRepository;
import pg.provingground.repository.TestRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final GroundRentalRepository groundRentalRepository;
    private final CarRentalRepository carRentalRepository;
    private final TestRepository testRepository;

    /** user가 time에 대여했던 시험장 기록들을 불러오기 */
    public List<GroundRentalHistory> getUsersGroundList(User user, LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return groundRentalRepository.findAllByUserAndTime(user, dateTime);
    }

    public List<TestHistory> getTestHistory(User user) {
        return testRepository.findAllByUser(user);
    }

    /** user가 time에 대여했던 차량 기록들을 볼러오기 */
    public List<CarRentalHistory> getUsersCarList(User user, LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return carRentalRepository.findAllByUserAndTime(user, dateTime);
    }

    @Transactional
    /** 입력 정보를 바탕으로 시험 진행 내역 추가 */
    public void addTest(TestForm testForm, User user) {
        GroundRental groundRental = groundRentalRepository.findOne(Long.valueOf(testForm.getGroundRentalId()));
        List<CarRental> carRentals = carRentalRepository.findByIds(testForm.getCarRentalIdsList());

        // 시간을 LocalDateTime으로 변경
        String dateTimeStr = testForm.getTestDate() + "T" + testForm.getTestTime();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        // 테스트를 생성 및 영속성 컨텍스트에 저장
        Test test = Test.createTest(testForm.getTitle(), testForm.getContents(), testForm.getPartners(), dateTime, user, carRentals, groundRental);
        testRepository.save(test);
    }


}
