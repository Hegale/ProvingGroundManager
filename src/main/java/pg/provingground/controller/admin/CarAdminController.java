package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.domain.Car;
import pg.provingground.dto.form.CarSearchForm;
import pg.provingground.domain.CarType;
import pg.provingground.dto.admin.CarDto;
import pg.provingground.dto.admin.CarForm;
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
    /** 차량번호로 차량 검색 */
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

    @GetMapping("/admin/car/select")
    /** 추가 등록할 차종 선택 */
    public String selectCar(@ModelAttribute CarSearchForm searchForm, Model model) {
        List<CarType> types = carTypeService.findCarTypesByCondition(searchForm);

        model.addAttribute("types", types);
        model.addAttribute("searchForm", searchForm);

        return "admin/car/car-select";
    }

    @GetMapping("/admin/car/select/{carTypeId}")
    /** 차량 등록을 위한 차량번호 입력 */
    public String addCarPage(@PathVariable Long carTypeId, Model model) {
        CarForm carForm = new CarForm();

        model.addAttribute("carForm", carForm);
        // TODO: 차량번호 형식이 맞게 입력되는지 확인하기, 사업차량 같은 것도 검증?

        return "admin/car/car-new";
    }

    @PostMapping("/admin/car/select/{carTypeId}")
    public String createCar(@PathVariable Long carTypeId, @ModelAttribute CarForm carForm) {
        carService.createCar(carTypeId, carForm.getNumber());
        return "redirect:/admin/car/list";
    }

    @GetMapping("/admin/car/{carId}/edit")
    /** 차량 정보 수정 */
    public String editCarPage(@PathVariable Long carId, Model model) {
        Car car = carService.findCar(carId);
        CarForm carForm = new CarForm(car.getNumber(), car.getFuel().toString());

        model.addAttribute("carForm", carForm);

        return "admin/car/car-edit";
    }

    @PostMapping("/admin/car/{carId}/edit")
    public String editCar(@ModelAttribute CarForm carForm, @PathVariable Long carId,
                          RedirectAttributes redirectAttributes, Model model) {
        // TODO: 입력값 검증

        model.addAttribute("carForm", carForm);
        try {
            carService.editCar(carId, carForm);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "잘못된 형식의 입력입니다.");
        }

        return "redirect:/admin/car/list";
    }

}
