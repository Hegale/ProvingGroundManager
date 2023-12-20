package pg.provingground.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.admin.TestSearchForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.dto.history.GroundRentalHistory;
import pg.provingground.dto.form.TestForm;
import pg.provingground.dto.history.TestHistory;
import pg.provingground.repository.CarRentalRepository;
import pg.provingground.repository.GroundRentalRepositoryImpl;
import pg.provingground.repository.TestRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestService {

    private final GroundRentalRepositoryImpl groundRentalRepository;
    private final CarRentalRepository carRentalRepository;
    private final TestRepository testRepository;

    public TestDto getTest(Long testId) {
        // TODO: 해당 유저가 아닐 시 접근 권한 없다는 Exception 발령
        Test test = testRepository.findOne(testId);
        return new TestDto(testId, test.getDateTime(), test.getType(), test.getPartners(), test.getTitle(),
                test.getContents(), test.getGroundRental().getGround().getName(), test.getUser().getUsername());
    }

    @Transactional
    /** 시험 삭제 */
    public void delete(Long testId) {
        Test test = testRepository.findOne(testId);
        testRepository.delete(test);
    }

    @Transactional
    /** 시험 수정 */
    public void edit(TestDto testDto) {
        Test test = testRepository.findOne(testDto.getTestId());
        testRepository.edit(test, testDto);
    }

    @Transactional
    /** 특정 테스트의 CarRental들을 리턴 */
    public List<CarRentalHistory> getTestCarRental(Long testId) {
        Test test = testRepository.findOne(testId);
        List<CarRental> carRentals = carRentalRepository.findByTest(test);

        return carRentals.stream()
                .map(carRental -> new CarRentalHistory(carRental.getCarRentalId(), carRental.getCar().getType().getName(), carRental.getStartTime(), carRental.getFileMetaData()))
                .collect(Collectors.toList());

    }

    /** user가 time에 대여했던 시험장 기록들을 불러오기 */
    public List<GroundRentalHistory> getUsersGroundList(User user, LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return groundRentalRepository.findAllByUserAndTime(user, dateTime);
    }

    public List<TestHistory> getTestHistory(User user, DateSearchForm dateSearchForm) {
        List<Test> tests;

        if (dateSearchForm.getStartDate() == null || dateSearchForm.getEndDate() == null) {
            tests = testRepository.findAllByUser(user);
        } else { // 날짜를 선택했을 경우
            LocalDate start = LocalDate.parse(dateSearchForm.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate end = LocalDate.parse(dateSearchForm.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            tests = testRepository.findAllByUserAndTimeInterval(user, LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        }

        List<TestHistory> testHistories = tests.stream()
                .map(TestHistory::new)
                .collect(Collectors.toList());
        return testHistories;
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
    public Long addTest(TestForm testForm, User user) {
        GroundRental groundRental = groundRentalRepository.findOne(Long.valueOf(testForm.getGroundRentalId()));
        List<CarRental> carRentals = carRentalRepository.findByIds(testForm.getCarRentalIdsList());

        // 시간을 LocalDateTime으로 변경
        String dateTimeStr = testForm.getTestDate() + "T" + testForm.getTestTime();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        // 테스트를 생성 및 영속성 컨텍스트에 저장
        Test test = Test.createTest(testForm.getTitle(), testForm.getContents(), testForm.getPartners(), dateTime, user, carRentals, groundRental);
        testRepository.save(test);

        // 각 테스트를 carRental에 적용
        for (CarRental carRental : carRentals) {
            carRental.setTest(test);
        }

        return test.getTestId();
    }

    /** [관리자] 조건에 따른 시험내역 검색 */
    public List<TestDto> searchTest(TestSearchForm searchForm) {
        // 날짜 조건이 입력된 경우, 해당 조건 추가
        if (searchForm.getStartDate() != null && searchForm.getEndDate() != null) {
            LocalDateTime start = DateTimeUtils.convertToStartOfDay(searchForm.getStartDate());
            LocalDateTime end = DateTimeUtils.convertToEndOfDay(searchForm.getEndDate());
            searchForm.setStartDateTime(start);
            searchForm.setEndDateTime(end);
        }
        return testRepository.searchTests(searchForm);
    }

    /** [관리자] 전체 시험내역 검색 */
    public List<TestDto> allTest() {
        return testRepository.findAllDto();
    }

    @Transactional
    public void addCarPath(MultipartFile file, Long carRentalId, Long testId) {
        FileMetaData metaData = getFileMetaData(file, testId);
        CarRental carRental = carRentalRepository.findOne(carRentalId);
        carRental.setFileMetaData(metaData); // 테스트에 파일 메타데이터를 저장
    }

    /*
    public void getCarPath(Long carRentalId){

    }

     */

    @Transactional
    public void processFile(MultipartFile file, Long testId) {
        FileMetaData fileMetaData = getFileMetaData(file, testId);
        Test test = testRepository.findOne(testId);
        test.setFileMetaData(fileMetaData); // 테스트에 파일 메타데이터를 저장
    }

    private FileMetaData getFileMetaData(MultipartFile file, Long testId) {
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        Long fileSize = file.getSize();
        LocalDateTime uploadTime = LocalDateTime.now();
        String filePath;

        try {
            filePath = saveFileToSystem(file, testId);
        } catch (IOException e) {
            System.out.println("오류 발생 : " + e);
            return null;
        }

        return new FileMetaData(fileName, fileType, fileSize, filePath, uploadTime);
    }

    /** 유저 입력 파일을 서버의 시스템에 저장 */
    private String saveFileToSystem(MultipartFile file, Long testId) throws IOException {
        String uploadDir = "/Users/juyeon/code/ProvingGround/userfiles/test-results/" + testId; // 서버의 특정 디렉토리
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        System.out.println("파일 이름: " + fileName);
        Path filePath = uploadPath.resolve(fileName);
        System.out.println("파일 경로: " + filePath);
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }

}
