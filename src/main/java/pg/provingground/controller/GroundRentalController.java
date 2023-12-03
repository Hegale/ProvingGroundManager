package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.service.GroundRentalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroundRentalController {

    private final GroundRentalService groundRentalService;

    @GetMapping("/ground_rental")
    /** 차량 대여 내역 */
    public String list(Model model) {
        // TODO: DTO 객체로 변환하여 내보내기
        List<GroundRental> rentals = groundRentalService.findRentals();
        model.addAttribute("rentals", rentals);
        return "ground_rental/ground_rental_history";
    }

}
