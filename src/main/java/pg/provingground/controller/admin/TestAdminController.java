package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.admin.TestSearchForm;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.service.DateTimeUtils;
import pg.provingground.service.TestService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestAdminController {

    private final TestService testService;

    @GetMapping("/admin/test/list")
    public String testList(@ModelAttribute TestSearchForm testSearchForm, RedirectAttributes redirectAttributes, Model model) {

        List<TestDto> tests;
        try {
            tests = testService.searchTest(testSearchForm);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 시험 내역 번호입니다!");
            return "redirect:/admin/test/list";
        }

        model.addAttribute("testSearchForm", testSearchForm);
        model.addAttribute("tests", tests);

        return "admin/test/test-list";
    }
}
