package pg.provingground.controller.admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import pg.provingground.domain.Station;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;
import pg.provingground.repository.StationRepository;
import pg.provingground.service.RefuelService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RefuelAdminController {

    private final RefuelService refuelService;
    private final StationRepository stationRepository;

    @GetMapping("/admin/refuel/list")
    public String list(@ModelAttribute RefuelSearchForm refuelSearchForm, Model model) {

        List<RefuelDto> refuelDtos = refuelService.searchRefuelsByConditions(refuelSearchForm);

        model.addAttribute("refuelSearchForm", refuelSearchForm);
        model.addAttribute("refuelDtos", refuelDtos);

        return "admin/refuel/refuel-list";
    }

    @GetMapping("/admin/station/list")
    public String stations(Model model) {
        List<Station> stations = stationRepository.findAll();

        model.addAttribute("stations", stations);

        return "admin/refuel/station-list";
    }

    @DeleteMapping("/admin/station/list/{stationId}")
    @Transactional
    /** 주유구 삭제 */
    public String stationDelete(@PathVariable Long stationId) {
        Station station = stationRepository.findOne(stationId);
        stationRepository.delete(station); // 예외 캐치
        return "redirect:/admin/station/list";
    }

}
