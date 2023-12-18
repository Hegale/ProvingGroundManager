package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pg.provingground.domain.Station;
import pg.provingground.dto.admin.RefuelDto;
import pg.provingground.dto.admin.RefuelSearchForm;
import pg.provingground.dto.admin.StationForm;
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

    @GetMapping("/admin/station/new")
    public String addStationPage(Model model) {
        StationForm stationForm = new StationForm();

        model.addAttribute("stationForm", stationForm);

        return "admin/refuel/station-new";
    }

    @PostMapping("/admin/station/new")
    public String addStation(@ModelAttribute StationForm stationForm) {
        System.out.println("주유구이름 : " + stationForm.getName() + "타입 : " + stationForm.getFuelType());
        refuelService.addStation(stationForm);
        return "redirect:/admin/station/list";
    }

    @GetMapping("/admin/station/{stationId}/edit")
    public String editStationPage(@PathVariable Long stationId, Model model) {
        Station station = stationRepository.findOne(stationId);
        StationForm stationForm = new StationForm(station);

        model.addAttribute("stationForm", stationForm);

        return "admin/refuel/station-edit";
    }

    @PostMapping("/admin/station/{stationId}/edit")
    @Transactional
    public String editStation(@PathVariable Long stationId, @ModelAttribute StationForm stationForm) {
        Station station = stationRepository.findOne(stationId);
        station.edit(stationForm);

        return "redirect:/admin/station/list";
    }

}
