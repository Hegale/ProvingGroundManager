package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.Ground;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;
import pg.provingground.dto.GroundRentalHistory;
import pg.provingground.repository.UserRepository;
import pg.provingground.service.AvailableTimeForm;
import pg.provingground.service.GroundRentalService;
import pg.provingground.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GroundRentalController {

    private final GroundRentalService groundRentalService;
    private final UserRepository userRepository; //임시. 테스트 이후 삭제
    private final UserService userService;

    @GetMapping("/ground-rental")
    /** 시험장 대여 내역 */
    public String list(Model model, Authentication auth) {
        User user = userService.getLoginUserByUsername(auth.getName());
        List<GroundRentalHistory> rentals = groundRentalService.findRentalHistory(user);
        model.addAttribute("rentals", rentals);
        return "ground/ground-rental-history";
    }

    @GetMapping("/ground-rental/select/{groundId}")
    /** 시험장 선택 후 날짜 선택 */
    public String selectDate(@PathVariable Long groundId, Model model, Authentication auth) {
        Long userId = userService.getLoginUserByUsername(auth.getName()).getUserId();
        GroundRentalForm form = new GroundRentalForm(userId, groundId);
        //List<AvailableTimeForm> times = groundRentalService.getAvailableTimeForms(groundId);

        model.addAttribute("form", form);
        // TODO: ajax로 불가능한 날짜 및 시간 처리하는 로직 구현
        //model.addAttribute("availableTimes", times);

        return "ground/ground-date-selection";
    }

    @PostMapping("/ground-rental/select/{groundId}")
    /** 예약에 필요한 정보들 확정 후 예약 실행 */
    public String rentGround(@PathVariable Long groundId, @ModelAttribute GroundRentalForm form, Authentication auth) {
        // 폼에서 입력받은 날짜 및 시간을 dateTime으로 변환
        String dateTimeString = form.getSelectedDate() + "T" + form.getSelectedTime() + ":00:00";
        LocalDateTime time = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Long userId = userService.getLoginUserByUsername(auth.getName()).getUserId();

        groundRentalService.rental(userId, groundId, time);

        return "redirect:/ground-rental"; // 대여 내역으로 이동
    }

    @PostMapping("/ground-rental/{groundRentalId}/cancel")
    public String cancelRental(@PathVariable("groundRentalId") Long groundRentalId) {

        groundRentalService.cancelRental(groundRentalId);

        return "redirect:/ground-rental";
    }




}
