package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pg.provingground.domain.CarType;
import pg.provingground.domain.Ground;
import pg.provingground.service.GroundService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroundController {

    private final GroundService groundService;

    @GetMapping("/ground_rental/new")
    public String list(Model model) {
        List<Ground> grounds = groundService.findItems();
        model.addAttribute("grounds", grounds);
        return "ground_rental/ground_selection";
    }

}
