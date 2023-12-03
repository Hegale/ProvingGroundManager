package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pg.provingground.domain.CarRental;
import pg.provingground.service.CarRentalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;

    @GetMapping("/car_rental")
    /** 차량 대여 내역 */
    public String list(Model model) {
        // TODO: DTO 객체로 변환하여 내보내기
        List<CarRental> carRentals = carRentalService.findRentals();
        model.addAttribute("rentals", carRentals);
        return "/car_rental/car_rent_history";
    }
}
