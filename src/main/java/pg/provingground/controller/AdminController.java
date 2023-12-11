package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import pg.provingground.domain.CarType;
import pg.provingground.dto.CarDto;
import pg.provingground.service.CarService;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CarTypeService carTypeService;
    private final CarService carService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin/admin-home"; //왜 검증안해?
    }

    @GetMapping("/admin/car-list")
    /** 차종으로 차량 검색 */
    public String carListPage(@ModelAttribute CarSearchForm typeSearchForm, Model model) {
        // 조건에 맞는 차종 가져오기
        List<CarType> types = carTypeService.findCarTypesByCondition(typeSearchForm);
        List<CarDto> cars = carService.findByCarType(types);
        System.out.println("form result: " + typeSearchForm.type + " | " + typeSearchForm.engine + " | " + typeSearchForm.name);

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);

        return "admin/car-list";
    }

    @GetMapping("/admin/car-list/car-number")
    /** 주유 전 차량 선택 */
    public String carNumberListPage(@RequestParam String carNumber, Model model) {
        // 차량 번호로 검색
        List<CarDto> cars = carService.findByCarNumber(carNumber);
        CarSearchForm typeSearchForm = new CarSearchForm();
        System.out.println("차량번호 : " + carNumber);
        for (CarDto car : cars) {
            System.out.println("차량 id : " + car.getCarId() + " | 차 : " + car.getName() + " | 차종 : " + car.getType());
        }

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);
        model.addAttribute("carNumber", carNumber);

        return "admin/car-list";
    }

}
