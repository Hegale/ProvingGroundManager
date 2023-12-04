package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.repository.UserRepository;
import pg.provingground.service.CarRentalService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;
    private final UserRepository userRepository; // 테스트를 위한 임시 선언. 이후 삭제

    @GetMapping("/car_rental")
    /** 차량 대여 내역 */
    public String list(Model model) {
        User user = userRepository.findOne(1L);
        List<CarRentalHistory> rentals = carRentalService.findRentalHistory(user);
        model.addAttribute("rentals", rentals);
        return "car_rental/car_rent_history";
    }

    @GetMapping("/car_rental/select/{carTypeId}")
    /** 차량 선택 후 날짜 선택 */
    public String selectDate(@PathVariable Long carTypeId, Model model) {
        // 모델에 데이터 추가
        CarRentalForm form = new CarRentalForm(1L, carTypeId);
        List<LocalDateTime> unavailableTimes = carRentalService.getUnavailableTimes(carTypeId);

        for (LocalDateTime time : unavailableTimes) {
            System.out.println("불가능한 시간 : " + time);
        }

        model.addAttribute("type", carTypeId);
        model.addAttribute("form", form);
        model.addAttribute("unavailableTimes", unavailableTimes);
        return "car_rental/car_date_selection";
    }

    @PostMapping("/car_rental/select/{carTypeId}")
    /** 대여에 필요한 정보들 확정 후 대여 시행 */
    public String rentCar(@PathVariable Long carTypeId, @ModelAttribute CarRentalForm form) {
        System.out.println("선택한 날짜 = " + form.getSelectedDate());
        System.out.println("선택한 시간 = " + form.getSelectedTime());
        return "home";
    }


    /*
    @GetMapping("/car_rental/{carTypeId}")
    /** 차량 선택 -> 날짜 선택
    public String dateSelect(@PathVariable Long carTypeId, Model model) {
        // TODO: carTypeId를 사용하여 예약 가능 날짜 및 시간 계산
        System.out.println("Controller method called with carTypeId: " + carTypeId);
        //model.addAttribute("carType", carTypeId); // 차량 타입 전달
        // model.addAttribute("dates", date); // 가능한 날짜 전달
        return "car_rental/car_date_selection";
    }*/

}
