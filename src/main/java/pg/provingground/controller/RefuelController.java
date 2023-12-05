package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pg.provingground.domain.CarType;
import pg.provingground.service.CarService;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefuelController {

    private final CarTypeService carTypeService;
    private final CarService carService;

    @GetMapping("/refuel/new")
    /** 주유 전 차량 선택 */
    public String carTypeList(@ModelAttribute CarSearchForm typeSearchForm, Model model) {
        // 차종으로 검색
        List<CarType> types = carTypeService.findCarTypesByCondition(typeSearchForm);
        System.out.println("form result: " + typeSearchForm.type + " | " + typeSearchForm.engine + " | " + searchForm.name);

        model.addAttribute("types", types);
        model.addAttribute("searchForm", typeSearchForm);

        return "refuel/refueling";
    }

}
