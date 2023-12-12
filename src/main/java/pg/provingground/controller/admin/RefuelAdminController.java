package pg.provingground.controller.admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;
import pg.provingground.service.RefuelService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefuelAdminController {

    private final RefuelService refuelService;

    @GetMapping("/admin/refuel/list")
    public String list(@ModelAttribute RefuelSearchForm refuelSearchForm, Model model) {

        List<RefuelDto> refuelDtos = refuelService.searchRefuelsByConditions(refuelSearchForm);

        model.addAttribute("refuelSearchForm", refuelSearchForm);
        model.addAttribute("refuelDtos", refuelDtos);

        return "admin/refuel/refuel-list";
    }
}
