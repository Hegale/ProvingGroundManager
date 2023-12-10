package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.dto.GroundRentalHistory;
import pg.provingground.dto.TestForm;
import pg.provingground.service.TestService;
import pg.provingground.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final UserService userService;

    @GetMapping("/test")
    // TODO: 시험일 등으로 검색하는 기능 추가. 관리자 기능 + 자기가 등록한 것만 볼 수 있게끔
    public String testHistory(Model model) {
        return "test/test-history";
    }

    @GetMapping("/test/new")
    // 시도
    public String newTest(@ModelAttribute TestForm testForm, Model model, Authentication auth) {

        User user = userService.getLoginUserByUsername(auth.getName());
        LocalDate date;
        System.out.println("선택한 날짜 : " + testForm.getTestDate());
        System.out.println("선택한 시간 : " + testForm.getTestTime());
        if (testForm.getTestDate() != null) {
            date = LocalDate.parse(testForm.getTestDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            date = null;
        }

        List<GroundRentalHistory> grounds = testService.getUsersGroundList(user, date);
        List<CarRentalHistory> cars = testService.getUsersCarList(user, date);

        model.addAttribute("testForm", testForm);
        model.addAttribute("grounds", grounds);
        model.addAttribute("cars", cars);

        return "test/test-write";
    }

    /*
    @GetMapping("/test/new")
    // TODO: 파일 저장 위한 객체, 날짜를 통한 대여 내역 검색 구현.. 이게 정상본인데 일단 수정시도할거임
    public String newTest(@ModelAttribute TestForm testForm, @RequestParam(required = false) String testDate,
                          @RequestParam(required = false) String testTime, Model model, Authentication auth) {

        testForm.setTestDate(testDate);
        User user = userService.getLoginUserByUsername(auth.getName());
        LocalDate date;
        System.out.println("선택한 시간 : " + testForm.getTestTime());
        if (testDate != null) {
            date = LocalDate.parse(testForm.getTestDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            date = null;
        }

        List<GroundRentalHistory> grounds = testService.getUsersGroundList(user, date);
        List<CarRentalHistory> cars = testService.getUsersCarList(user, date);

        model.addAttribute("testForm", testForm);
        model.addAttribute("grounds", grounds);
        model.addAttribute("cars", cars);

        return "test/test-write";
    }
    */

    @PostMapping("/test/new")
    // TODO: 인자로 받아올 친구들 추가
    public String newTestResult(@RequestParam String carRentalIds, @ModelAttribute TestForm testForm) {
        List<Long> carRentalIdList = Arrays.stream(carRentalIds.split(","))
                .map(Long::valueOf)
                .toList();

        System.out.println("차량은?? : " + carRentalIds);
        return "redirect:/";
    }

}