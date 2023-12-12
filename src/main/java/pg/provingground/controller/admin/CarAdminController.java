package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.controller.CarSearchForm;
import pg.provingground.domain.CarType;
import pg.provingground.dto.admin.CarDto;
import pg.provingground.service.CarService;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarAdminController {

    private final CarTypeService carTypeService;
    private final CarService carService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin/admin-home"; //왜 검증안해?
    }

    @GetMapping("/admin/car/list")
    /** 차종으로 차량 검색 */
    public String carListPage(@ModelAttribute CarSearchForm typeSearchForm, Model model) {
        // 조건에 맞는 차종 가져오기
        List<CarType> types = carTypeService.findCarTypesByCondition(typeSearchForm);
        List<CarDto> cars = carService.findByCarType(types);

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);

        return "admin/car/car-list";
    }

    @GetMapping("/admin/car/list/car-number")
    /** 주유 전 차량 선택 */
    public String carNumberListPage(@RequestParam String carNumber, Model model) {
        // 차량 번호로 검색
        List<CarDto> cars = carService.findByCarNumber(carNumber);
        CarSearchForm typeSearchForm = new CarSearchForm();

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);
        model.addAttribute("carNumber", carNumber);

        return "admin/car/car-list";
    }

    @DeleteMapping("/admin/car/list/{carId}")
    /** 차량 삭제 */
    public String carDelete(@PathVariable Long carId) {
        carService.deleteCar(carId); // 예외 캐치
        return "redirect:/admin/car/list";
    }

}
