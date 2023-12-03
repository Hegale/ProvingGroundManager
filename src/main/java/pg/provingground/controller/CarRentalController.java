package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.repository.UserRepository;
import pg.provingground.service.CarRentalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;
    private final UserRepository userRepository; // 테스트를 위한 임시 선언. 이후 삭제

    @GetMapping("/car_rental")
    /** 차량 대여 내역 */
    public String list(Model model) {
        // TODO: DTO 객체로 변환하여 내보내기
        User user = userRepository.findOne(1L);
        List<CarRentalHistory> rentals = carRentalService.findRentalHistory(user);
        model.addAttribute("rentals", rentals);
        return "car_rental/car_rent_history";
    }

    /*
    @GetMapping("/car_rental")
    /** 차량 선택 -> 날짜 선택
    public String carRental(@RequestParam("carTypeId") Long carTypeId, Model model) {
        // TODO: carTypeId를 사용하여 예약 가능 날짜 및 시간 계산
        model.addAttribute("carType", carTypeId); // 차량 타입 전달
        // model.addAttribute("dates", date); // 가능한 날짜 전달
        return "car_date_selection";
    }
    */
}
