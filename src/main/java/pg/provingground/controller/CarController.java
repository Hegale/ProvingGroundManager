package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pg.provingground.domain.CarType;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarTypeService carTypeService;

    @GetMapping("/car_rental/new")
    /** 차량 선택 페이지 */
    public String carTypeList(Model model) {
        List<CarType> types = carTypeService.findCarTypes();
        CarTypeSearchForm searchForm = new CarTypeSearchForm();

        model.addAttribute("types", types);
        model.addAttribute("searchForm", searchForm);

        return "car_rental/car_selection";
    }

    @PostMapping("/car_rental/new")
    public String searchCarType(@ModelAttribute CarTypeSearchForm searchForm) {
        System.out.println("form result: " + searchForm.type + " | " + searchForm.engine + " | " + searchForm.name);
        return "redirect:/car_rental/new";
    }


}
