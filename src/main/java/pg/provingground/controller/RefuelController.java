package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.domain.CarType;
import pg.provingground.domain.Station;
import pg.provingground.dto.CarDto;
import pg.provingground.repository.StationRepository;
import pg.provingground.service.CarService;
import pg.provingground.service.CarTypeService;
import pg.provingground.service.RefuelService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefuelController {

    private final CarTypeService carTypeService;
    private final CarService carService;
    private final StationRepository stationRepository;

    @GetMapping("/refuel/new")
    /** 차종으로 차량 검색 */
    public String carList(@ModelAttribute CarSearchForm typeSearchForm, Model model) {
        // 조건에 맞는 차종 가져오기
        List<CarType> types = carTypeService.findCarTypesByCondition(typeSearchForm);
        List<CarDto> cars = carService.findByCarType(types);
        System.out.println("form result: " + typeSearchForm.type + " | " + typeSearchForm.engine + " | " + typeSearchForm.name);

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);

        return "refuel/fuel_select_car";
    }

    @GetMapping("/refuel/new/car_number")
    /** 주유 전 차량 선택 */
    public String carListByNumber(@RequestParam String carNumber, Model model) {
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

        return "refuel/fuel_select_car";
    }

    @GetMapping("/refuel/select/{carId}")
    /** 차 선택 후 주유 */
    public String chooseAmount(@PathVariable("carId") Long carRentalId, Model model) {
        List<Station> stations = stationRepository.findAll();

        model.addAttribute("stations", stations);

        return "refuel/refueling";
    }

    @PostMapping("/refuel/select/{carId}")
    /** 차 선택 후 주유 */
    public String refueling(@PathVariable("carId") Long carRentalId, @RequestParam Long stationId, @RequestParam Long amount) {
        // amount의 유효성 체크
        return "refuel/fueling_history";
    }

}
