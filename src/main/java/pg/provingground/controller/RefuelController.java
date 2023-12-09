package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.domain.CarType;
import pg.provingground.domain.Refuel;
import pg.provingground.domain.Station;
import pg.provingground.dto.CarDto;
import pg.provingground.repository.StationRepository;
import pg.provingground.service.CarService;
import pg.provingground.service.CarTypeService;
import pg.provingground.service.RefuelService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefuelController {

    private final CarTypeService carTypeService;
    private final CarService carService;
    private final StationRepository stationRepository;
    private final RefuelService refuelService;

    @GetMapping("/refuel/new")
    /** 차종으로 차량 검색 */
    public String carList(@ModelAttribute CarSearchForm typeSearchForm, Model model) {
        // 조건에 맞는 차종 가져오기
        List<CarType> types = carTypeService.findCarTypesByCondition(typeSearchForm);
        List<CarDto> cars = carService.findByCarType(types);
        System.out.println("form result: " + typeSearchForm.type + " | " + typeSearchForm.engine + " | " + typeSearchForm.name);

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", typeSearchForm);

        return "refuel/fuel-select-car";
    }

    @GetMapping("/refuel/new/car-number")
    /** 주유 전 차량 선택 */
    public String carListByNumber(@RequestParam String carNumber, Model model) {
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

        return "refuel/fuel-select-car";
    }

    @GetMapping("/refuel/select/{carId}")
    /** 차 선택 후 주유 */
    public String chooseAmount(@PathVariable("carId") Long carId, Model model) {
        List<Station> stations = stationRepository.findAll();

        model.addAttribute("stations", stations);
        model.addAttribute("carId", carId); //없어도 되나?

        return "refuel/refueling";
    }

    @PostMapping("/refuel/select/{carId}")
    /** 차 선택 후 주유 */
    public String refueling(@PathVariable("carId") Long carId, @RequestParam Long stationId, @RequestParam Long amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long validAmount = carService.validFuelAmount(carId, amount);

        // TODO: 유효한 주유구인지 확인하는 service method 호출
        System.out.println("carId:" + carId + " | stationId:" + stationId + " | validAmount:" + validAmount);
        refuelService.refuel(username, carId, stationId, LocalDateTime.now(), validAmount);

        // 주유 후 주유 내역으로 이동
        return "redirect:/refuel";
    }

    @GetMapping("/refuel")
    /** 주유 내역 확인 */
    public String fuelHistory(Model model) {
        // 해당 유저의 주유기록 받아오기
        // TODO: Dto로 변환!
        //List<Refuel> refuels = refuelService.findUserRefuel(1L); 이거 못읽어옴..왜??

        List<Refuel> refuels = refuelService.findAllRefuel();

        model.addAttribute("refuels", refuels);

        return "refuel/fueling-history";
    }

}
