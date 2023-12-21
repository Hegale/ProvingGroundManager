package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.Test;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.dto.history.GroundRentalHistory;
import pg.provingground.dto.form.TestForm;
import pg.provingground.dto.history.TestHistory;
import pg.provingground.security.CheckOwnership;
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
    private final CarRentalService carRentalService;

    @GetMapping("/test")
    public String testHistory(@ModelAttribute DateSearchForm dateSearchForm, Model model, Authentication auth) {
        User user = userService.getLoginUserByUsername(auth.getName());
        List<TestHistory> tests = testService.getTestHistory(user, dateSearchForm);

        model.addAttribute("dateSearchForm", dateSearchForm);
        model.addAttribute("tests", tests);

        return "test/test-history";
    }

    @GetMapping("/test/{testId}/result")
    @CheckOwnership(serviceName = "testService")
    public String testResult(@PathVariable Long testId, Model model){

        TestDto test = testService.getTest(testId);
        List<CarRentalHistory> carRentalHistories = testService.getTestCarRental(testId);

        model.addAttribute("test", test);
        model.addAttribute("carRentals", carRentalHistories);

        return "test/test-result";
    }

    @PostMapping("/test/{testId}/result")
    //@CheckOwnership(serviceName = "testService")
    public String testResult(@PathVariable Long testId, @RequestParam("carRentalId") Long carRentalId,
                             @RequestParam(value = "file") MultipartFile file) {
        if (file != null) {
            testService.addCarPath(file, carRentalId, testId);
        }
        return "redirect:/test/{testId}/result";
    }

    @GetMapping("/test/{testId}/{carRentalId}/result")
    @CheckOwnership(serviceName = "testService")
    public String carTestResult(@PathVariable Long testId, @PathVariable Long carRentalId, Model model) {
        TestDto testDto = testService.getTest(testId);
        String carPathFile = carRentalService.getTestCarPath(carRentalId);

        model.addAttribute("test", testDto);
        model.addAttribute("groundName", testDto.getGroundName());
        model.addAttribute("carPath", carPathFile);

        return "test/test-result-car";
    }

    @GetMapping("/test/{testId}/edit")
    @CheckOwnership(serviceName = "testService")
    /** 특정 시험 내역을 수정 혹은 삭제, 해당 페이지로 이동 */
    public String editTest(@PathVariable Long testId, Model model) {
        TestDto test = testService.getTest(testId);

        model.addAttribute("test", test);

        return "test/test-edit"; // 수정 및 삭제 전용 페이지로 이동.
    }

    @PostMapping("/test/{testId}/edit")
    @CheckOwnership(serviceName = "testService")
    public String editTest(@PathVariable Long testId, @ModelAttribute TestDto testDto,
                           @RequestParam(value = "files", required = false) MultipartFile file) {
        testService.edit(testDto);
        if (file != null) {
            testService.processFile(file, testId);
        }

        return "redirect:/test";
    }

    @DeleteMapping("/test/{testId}/edit")
    @CheckOwnership(serviceName = "testService")
    /** 시험 내역 삭제 삭제 */
    public String testDelete(@PathVariable Long testId) {
        testService.delete(testId);
        return "redirect:/test";
    }

    @GetMapping("/test/new")
    /** 새로운 시험 내역 작성 */
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
    public String newTestResult(@ModelAttribute TestForm testForm, Authentication auth, RedirectAttributes redirectAttributes,
                                @RequestParam(value = "files", required = false) MultipartFile[] files) {
        // 필수요소들이 선택되지 않은 경우, 오류 발생
        if (testForm.getGroundRentalId() == null || testForm.getCarRentalIds() == null) {
            redirectAttributes.addFlashAttribute("시험장과 차량은 필수 입력사항입니다.");
            return "redirect:/test/new";
        }

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
