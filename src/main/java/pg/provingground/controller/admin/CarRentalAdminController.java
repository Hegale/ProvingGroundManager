package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.controller.CarSearchForm;
import pg.provingground.dto.admin.CarDto;
import pg.provingground.dto.admin.CarRentalDto;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.dto.admin.CarRentalSearchForm;
import pg.provingground.service.CarRentalService;
import pg.provingground.service.CarService;
import pg.provingground.service.CarTypeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarRentalAdminController {

    private final CarRentalService carRentalService;
    private final CarService carService;
    private final CarTypeService carTypeService;

    @GetMapping("/admin/car-rental/list")
    /** 차량 대여 내역, 차종으로 검색 */
    public String list(@ModelAttribute CarRentalSearchForm carRentalSearchForm, Model model) {

        List<CarRentalDto> carRentalDtos = carRentalService.getRentalsByConditions(carRentalSearchForm);

        model.addAttribute("carRentalSearchForm", carRentalSearchForm);
        model.addAttribute("carRentalDtos", carRentalDtos);

        return "admin/car/car-rental-list";
    }



}
