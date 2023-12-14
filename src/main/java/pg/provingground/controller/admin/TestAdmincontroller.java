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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestAdmincontroller {

    private final TestService testService;

    @GetMapping("/admin/test/list")
    public String testList(@ModelAttribute TestSearchForm testSearchForm, @ModelAttribute DateSearchForm dateSearchForm, Model model) {

        List<TestDto> tests;

        if (dateSearchForm.getStartDate() != null && dateSearchForm.getEndDate() != null) {
            // 지정 날짜 범위를 변환하여 입력
            testSearchForm.setStartTime(DateTimeUtils.convertToStartOfDay(dateSearchForm.getStartDate()));
            testSearchForm.setEndTime(DateTimeUtils.convertToEndOfDay(dateSearchForm.getEndDate()));
            tests = testService.searchTest(testSearchForm);
        } else {
            tests = testService.allTest();
        }

        model.addAttribute("testSearchForm", testSearchForm);
        model.addAttribute("dateSearchForm", dateSearchForm);
        model.addAttribute("Tests", tests);

        return "admin/test/test-list";
    }
}
