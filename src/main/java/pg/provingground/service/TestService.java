package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.admin.TestSearchForm;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.dto.history.GroundRentalHistory;
import pg.provingground.dto.form.TestForm;
import pg.provingground.dto.history.TestHistory;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.GroundRentalRepository;
import pg.provingground.repository.TestRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    /** [관리자] 조건에 따른 시험내역 검색 */
    public List<TestDto> searchTest(TestSearchForm testSearchForm) {
        // TODO: 오류 핸들링
        return testRepository.searchTests(testSearchForm);
    }

    /** [관리자] 전체 시험내역 검색 */
    public List<TestDto> allTest() {
        return testRepository.findAll();
    }


}
