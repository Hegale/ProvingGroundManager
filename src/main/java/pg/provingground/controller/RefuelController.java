package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String carTypeListByNumber(@RequestParam String carNumber, Model model) {
        // 차량 번호로 검색
        // TODO: 차량 번호로 검색 안되고 있음
        List<CarDto> cars = carService.findByCarNumber(carNumber);
        CarSearchForm typeSearchForm = new CarSearchForm(); //테스트
        System.out.println("차량번호 : " + carNumber);
        for (CarDto car : cars) {
            System.out.println("차량 id : " + car.getCarId() + " | 차 : " + car.getName() + " | 차종 : " + car.getType());
        }

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);
        model.addAttribute("carNumber", carNumber);

        return "refuel/refueling";
    }

    @PostMapping("/refuel/select/{carId}")
    /** 차 선택 후 주유 */
    public String returnRental(@PathVariable("carRentalId") Long carRentalId) {
        // TODO: 주유 메소드 구현
        return "refuel/fueling_history";
    }

}
