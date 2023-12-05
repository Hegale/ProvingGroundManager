package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.domain.CarType;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarTypeService carTypeService;

    @GetMapping("/car_rental/new")
    /** 차량 선택 페이지. 검색에 따른 차종 결과 출력 */
    public String carTypeList(@ModelAttribute CarSearchForm searchForm, Model model) {
        List<CarType> types = carTypeService.findCarTypesByCondition(searchForm);
        System.out.println("form result: " + searchForm.type + " | " + searchForm.engine + " | " + searchForm.name);

        model.addAttribute("types", types);
        model.addAttribute("searchForm", searchForm);

        return "car_rental/car_selection";
    }


    @PostMapping("/car_rental/new")
    public String searchCarType(@ModelAttribute CarSearchForm searchForm, RedirectAttributes redirectAttributes) {
        System.out.println("form result: " + searchForm.type + " | " + searchForm.engine + " | " + searchForm.name);

        // redirectAttributes.addAttribute(searchForm); redirect에 객체 자체를 넘기려 해서 문제 발생한듯함
        redirectAttributes.addAttribute("type", searchForm.getType());
        redirectAttributes.addAttribute("engine", searchForm.getEngine());
        redirectAttributes.addAttribute("name", searchForm.getName());

        return "redirect:/car_rental/new/search";
    }

    @GetMapping("/car_rental/new/search")
    //차종 조건을 통해 검색
    public String carTypeListByCondition(Model model, @ModelAttribute("searchForm") CarSearchForm searchForm) {
        List<CarType> types = carTypeService.findCarTypesByCondition(searchForm);

        model.addAttribute("types", types);
        model.addAttribute("searchForm", searchForm);

        return "car_rental/car_selection";
    }


}
