package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.controller.CarSearchForm;
import pg.provingground.dto.admin.CarDto;
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

    /*
    @GetMapping("/admin/car-rental/list")
     차량 대여 내역, 차종으로 검색
    public String list(@ModelAttribute CarRentalSearchForm carRentalSearchForm, Model model) {

        // 해당 유저의 차량 대여 내역을 보여준다.
        List<CarRentalHistory> rentals = carRentalService.getAllRentals();

        List<> types = carTypeService.findByCondition(carTypeSearchForm);
        List<CarDto> cars = carService.findByCarType(types);
        System.out.println("form result: " + carTypeSearchForm.type + " | " + carTypeSearchForm.engine + " | " + typeSearchForm.name);

        model.addAttribute("cars", cars);
        model.addAttribute("typeSearchForm", carTypeSearchForm);

        model.addAttribute("rentals", rentals);
        model.addAttribute("dateSearchForm", dateSearchForm); // 날짜를 통한 검색 위한 폼

        return "admin/car/car-rental-list";
    }
    */


}
