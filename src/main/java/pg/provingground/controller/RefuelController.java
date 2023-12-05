package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;
import pg.provingground.dto.CarDto;
import pg.provingground.service.CarService;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefuelController {

    private final CarTypeService carTypeService;
    private final CarService carService;

    @GetMapping("/refuel/new")
    /** 차종으로 차량 검색 */
    public String carTypeList(@ModelAttribute CarSearchForm typeSearchForm, Model model) {
        // 조건에 맞는 차종 가져오기
        List<CarType> types = carTypeService.findCarTypesByCondition(typeSearchForm);
        List<CarDto> cars = carService.findByCarType(types);
        System.out.println("form result: " + typeSearchForm.type + " | " + typeSearchForm.engine + " | " + typeSearchForm.name);

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);

        return "refuel/refueling";
    }

    @GetMapping("/refuel/new/car_number")
    /** 주유 전 차량 선택 */
    public String carTypeListByNumber(@ModelAttribute String number, Model model) {
        // 차량 번호로 검색
        List<CarDto> cars = carService.findByCarNumber(number);
        System.out.println("차량번호 : " + number);

        model.addAttribute("cars", cars);
        model.addAttribute("number", number);

        return "refuel/refueling";
    }

}
