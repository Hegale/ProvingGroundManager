package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pg.provingground.domain.Test;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.admin.TestSearchForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.service.DateTimeUtils;
import pg.provingground.service.TestService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestAdmincontroller {

    private final TestService testService;

    @GetMapping("/admin/test/list")
    public String testList(@ModelAttribute TestSearchForm testSearchForm, Model model) {

        List<TestDto> tests;

        tests = testService.searchTest(testSearchForm);
        // tests = testService.allTest();

        for (TestDto test : tests) {
            System.out.println("시험내역 = " + test.getTestId());
        }

        model.addAttribute("testSearchForm", testSearchForm);
        //model.addAttribute("dateSearchForm", dateSearchForm);
        model.addAttribute("tests", tests);

        return "admin/test/test-list";
    }
}
