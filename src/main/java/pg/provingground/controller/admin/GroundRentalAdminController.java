package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.dto.admin.CarRentalDto;
import pg.provingground.dto.admin.GroundRentalDto;
import pg.provingground.dto.admin.GroundRentalSearchForm;
import pg.provingground.service.GroundRentalService;
import pg.provingground.service.GroundService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroundRentalAdminController {

    private final GroundRentalService groundRentalService;

    @GetMapping("/admin/ground-rental/list")
    public String list(@ModelAttribute GroundRentalSearchForm groundRentalSearchForm, RedirectAttributes redirectAttributes, Model model) {
        List<GroundRentalDto> groundRentalDtos;
        try {
            groundRentalDtos = groundRentalService.searchGroundRentalsByConditions(groundRentalSearchForm);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "유효하지 않은 예약번호입니다!");
            return "redirect:/admin/ground-rental/list";
        }

        model.addAttribute("groundRentalSearchForm", groundRentalSearchForm);
        model.addAttribute("groundRentalDtos", groundRentalDtos);

        return "admin/ground/ground-rental-list";
    }
}
