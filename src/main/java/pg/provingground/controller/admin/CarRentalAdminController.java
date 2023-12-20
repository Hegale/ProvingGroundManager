package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.dto.admin.CarRentalDto;
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
    public String list(@ModelAttribute CarRentalSearchForm carRentalSearchForm, RedirectAttributes redirectAttributes, Model model) {

        List<CarRentalDto> carRentalDtos;
        try {
            carRentalDtos = carRentalService.getRentalsByConditions(carRentalSearchForm);
        } catch (NumberFormatException e) {
            System.out.println("유효하지않다구요");
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 대여번호입니다!");
            return "redirect:/admin/car-rental/list";
        }

        model.addAttribute("carRentalSearchForm", carRentalSearchForm);
        model.addAttribute("carRentalDtos", carRentalDtos);

        return "admin/car/car-rental-list";
    }



}
