package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;
import pg.provingground.dto.GroundRentalHistory;
import pg.provingground.repository.UserRepository;
import pg.provingground.service.GroundRentalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroundRentalController {

    private final GroundRentalService groundRentalService;
    private final UserRepository userRepository; //임시. 테스트 이후 삭제

    @GetMapping("/ground_rental")
    /** 시험장 대여 내역 */
    public String list(Model model) {
        User user = userRepository.findOne(1L);
        List<GroundRentalHistory> rentals = groundRentalService.findRentalHistory(user);
        model.addAttribute("rentals", rentals);
        return "ground_rental/ground_rental_history";
    }

}
