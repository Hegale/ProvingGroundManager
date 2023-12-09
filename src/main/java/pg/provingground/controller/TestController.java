package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import pg.provingground.dto.TestForm;

@Controller
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    // TODO: 시험일 등으로 검색하는 기능 추가. 관리자 기능 + 자기가 등록한 것만 볼 수 있게끔
    public String testHistory(Model model) {
        return "test/test-history";
    }

    @GetMapping("/test/new")
    // TODO: 파일 저장 위한 객체, 날짜를 통한 대여 내역 검색 구현
    public String newTest(@ModelAttribute TestForm testForm, @RequestParam(required = false) String testDate,
                          Model model, Authentication auth) {

        testForm.setTestDate(testDate);
        model.addAttribute("testForm", testForm);

        return "test/test-write";
    }

}
