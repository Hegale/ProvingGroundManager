package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.dto.DateSearchForm;
import pg.provingground.service.CarRentalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarRentalAdminController {

    private final CarRentalService carRentalService;

    @GetMapping("/admin/car-rental/list")
    /** 차량 대여 내역 */
    public String list(@ModelAttribute DateSearchForm dateSearchForm, Model model, Authentication auth) {
        // 해당 유저의 차량 대여 내역을 보여준다.
        List<CarRentalHistory> rentals = carRentalService.getAllRentals();

        model.addAttribute("rentals", rentals);
        model.addAttribute("dateSearchForm", dateSearchForm); // 날짜를 통한 검색 위한 폼

        return "admin/car/car-rental-list";
    }

}
