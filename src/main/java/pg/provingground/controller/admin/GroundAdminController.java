package pg.provingground.controller.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.domain.Ground;
import pg.provingground.dto.admin.CarForm;
import pg.provingground.dto.admin.GroundForm;
import pg.provingground.service.GroundService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroundAdminController {

    private final GroundService groundService;

    @GetMapping("/admin/ground/list")
    public String list(Model model) {
        List<Ground> grounds = groundService.findItems();
        model.addAttribute("grounds", grounds);
        return "admin/ground/ground-list";
    }

    @DeleteMapping("/admin/ground/list/{groundId}")
    /** 주행시험장 삭제 */
    public String groundDelete(@PathVariable Long groundId) {
        groundService.deleteGround(groundId); // 예외 캐치
        return "redirect:/admin/ground/list";
    }

    @GetMapping("/admin/ground/new")
    public String addGroundPage(Model model) {
        GroundForm groundForm = new GroundForm();

        model.addAttribute("groundForm", groundForm);
        return "admin/ground/ground-new";
    }

    @PostMapping("/admin/ground/new")
    public String addGround(@ModelAttribute GroundForm groundForm, RedirectAttributes redirectAttributes) {
        try {
            groundService.addGround(groundForm);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/ground/list";
    }

    @GetMapping("/admin/ground/{groundId}/edit")
    public String editGroundPage(@PathVariable Long groundId, Model model) {
        Ground ground = groundService.findOne(groundId);
        GroundForm groundForm = new GroundForm(ground);

        model.addAttribute("groundForm", groundForm);

        return "admin/ground/ground-edit";
    }

    @PostMapping("/admin/ground/{groundId}/edit")
    public String editGround(@ModelAttribute GroundForm groundForm, @PathVariable Long groundId,
                             RedirectAttributes redirectAttributes, Model model) {

        try {
            groundService.editGround(groundId, groundForm);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/ground/list";
    }


}
