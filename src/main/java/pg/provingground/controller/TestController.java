package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.Test;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.dto.history.GroundRentalHistory;
import pg.provingground.dto.form.TestForm;
import pg.provingground.dto.history.TestHistory;
import pg.provingground.service.CarRentalService;
import pg.provingground.service.TestService;
import pg.provingground.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final UserService userService;

    @GetMapping("/test")
    // TODO: 시험일 등으로 검색하는 기능 추가. 관리자 기능 + 자기가 등록한 것만 볼 수 있게끔
    public String testHistory(Model model, Authentication auth) {
        User user = userService.getLoginUserByUsername(auth.getName());
        List<TestHistory> tests = testService.getTestHistory(user);

        model.addAttribute("tests", tests);

        return "test/test-history";
    }

    @GetMapping("/test/{testId}/result")
    public String testResult(@PathVariable Long testId, Model model){

        TestDto test = testService.getTest(testId);
        List<CarRentalHistory> carRentalHistories = testService.getTestCarRental(testId);

        model.addAttribute("test", test);
        model.addAttribute("carRentals", carRentalHistories);

        return "test/test-result";
    }

    @PostMapping("/test/{testId}/result")
    public String testResult(@PathVariable Long testId, @RequestParam("carRentalId") Long carRentalId,
                             @RequestParam(value = "file") MultipartFile file) {
        if (file != null) {
            testService.addCarPath(file, carRentalId, testId);
        }
        return "redirect:/test/{testId}/result";
    }

    @GetMapping("/test/{testId}/{carRentalId}/result")
    public String carTestResult(@PathVariable Long testId, @PathVariable Long carRentalId, Model model) {
        TestDto testDto = testService.getTest(testId);

        model.addAttribute("test", testDto);

        return "test/test-result-car";
    }

    @GetMapping("/test/{testId}/edit")
    /** 특정 시험 내역을 수정 혹은 삭제, 해당 페이지로 이동 */
    public String fixHistory(@PathVariable Long testId) {
        return "test/test-edit"; // 수정 및 삭제 전용 페이지로 이동.
    }

    @GetMapping("/test/new")
    // 시도
    public String newTest(@ModelAttribute TestForm testForm, Model model, Authentication auth) {

        User user = userService.getLoginUserByUsername(auth.getName());
        LocalDateTime dateTime;

        // 폼에서 받아온 시간을 LocalDateTime으로 변경
        if (testForm.getTestDate() != null && testForm.getTestTime() != null) {
            String dateTimeStr = testForm.getTestDate() + "T" + testForm.getTestTime();

            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        } else {
            dateTime = null;
        }

        // 해당 날짜의 대여내역을 각각 받아오기
        List<GroundRentalHistory> grounds = testService.getUsersGroundList(user, dateTime);
        List<CarRentalHistory> cars = testService.getUsersCarList(user, dateTime);

        model.addAttribute("testForm", testForm);
        model.addAttribute("grounds", grounds);
        model.addAttribute("cars", cars);

        return "test/test-write";
    }

    @PostMapping("/test/new")
    public String newTestResult(@ModelAttribute TestForm testForm, Authentication auth,
                                @RequestParam(value = "files", required = false) MultipartFile[] files) {

        // 여러 개 받아온 차량대여 목록을 set
        testForm.setCarRentalIdsList(Arrays.stream(testForm.getCarRentalIds().split(","))
                .map(Long::valueOf)
                .toList());

        String userName = auth.getName();
        Long testId = testService.addTest(testForm, userService.getLoginUserByUsername(userName));

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                testService.processFile(file, testId);
            }
        }

        return "redirect:/test";
    }

}
