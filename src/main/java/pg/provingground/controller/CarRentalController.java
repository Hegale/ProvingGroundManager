package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.repository.UserRepository;
import pg.provingground.service.AvailableTimeForm;
import pg.provingground.service.CarRentalService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PostMapping("/car_rental/{carRentalId}/return")
    public String returnRental(@PathVariable("carRentalId") Long carRentalId) {
        carRentalService.cancelRental(carRentalId);
        return "redirect:/car_rental";
    }

    @GetMapping("/car_rental/select/{carTypeId}")
    /** 차량 선택 후 날짜 선택 페이지 */
    public String selectDate(@PathVariable Long carTypeId, Model model) {
        CarRentalForm form = new CarRentalForm(1L, carTypeId);
        List<AvailableTimeForm> times = carRentalService.getAvailableTimeForms(carTypeId);

        model.addAttribute("type", carRentalService.findType(carTypeId));
        model.addAttribute("form", form);
        // TODO: ajax로 불가능한 날짜 및 시간 처리하는 로직 구현
        model.addAttribute("availableTimes", times);

        return "car_rental/car_date_selection";
    }

    @PostMapping("/car_rental/select/{carTypeId}")
    /** 대여에 필요한 정보들 확정 후 대여 시행 */
    public String rentCar(@PathVariable Long carTypeId, @ModelAttribute CarRentalForm form) {
        // 폼에서 입력받은 날짜 및 시간을 dateTime으로 변환
        String dateTimeString = form.getSelectedDate() + "T" + form.getSelectedTime() + ":00:00";
        LocalDateTime time = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Long carId = carRentalService.getSchedulableCar(carTypeId, time);
        carRentalService.rental(1L, carId, time);

        return "redirect:/car_rental"; // 대여 내역으로 이동
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
