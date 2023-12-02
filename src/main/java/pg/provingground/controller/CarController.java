package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pg.provingground.domain.CarType;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarTypeService carTypeService;

    @GetMapping("car_rental")
    public String carTypeList(Model model) {
        List<CarType> types = carTypeService.findCarTypes();
        model.addAttribute("types", types);
        return "car_rental/car_selection";
    }
}
