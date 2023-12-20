package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String list(@ModelAttribute GroundRentalSearchForm groundRentalSearchForm, Model model) {
        List<GroundRentalDto> groundRentalDtos = groundRentalService.searchGroundRentalsByConditions(groundRentalSearchForm);

        model.addAttribute("groundRentalSearchForm", groundRentalSearchForm);
        model.addAttribute("groundRentalDtos", groundRentalDtos);

        return "admin/ground/ground-rental-list";
    }
}
