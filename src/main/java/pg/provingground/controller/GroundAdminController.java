package pg.provingground.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pg.provingground.domain.Ground;
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
    /** 차량 삭제 */
    public String carDelete(@PathVariable Long groundId) {
        groundService.deleteGround(groundId); // 예외 캐치
        return "redirect:/admin/ground/list";
    }

}
