package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.domain.User;
import pg.provingground.dto.form.DateSearchForm;
import pg.provingground.dto.form.GroundRentalForm;
import pg.provingground.dto.history.GroundRentalHistory;
import pg.provingground.exception.NoAvailableGroundException;
import pg.provingground.repository.UserRepository;
import pg.provingground.service.GroundRentalService;
import pg.provingground.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GroundRentalController {

    private final GroundRentalService groundRentalService;
    private final UserRepository userRepository; //임시. 테스트 이후 삭제
    private final UserService userService;

    @GetMapping("/ground-rental")
    /** 시험장 대여 내역 */
    public String list(@ModelAttribute DateSearchForm dateSearchForm, Model model, Authentication auth) {
        User user = userService.getLoginUserByUsername(auth.getName());
        List<GroundRentalHistory> rentals = groundRentalService.findRentalHistory(user, dateSearchForm);

        model.addAttribute("rentals", rentals);
        model.addAttribute("dateSearchForm", dateSearchForm);
        return "ground/ground-rental-history";
    }


    @GetMapping("/ground-rental/select/{groundId}")
    /** 시험장 선택 후 날짜 선택 */
    public String selectDate(@PathVariable Long groundId, Model model, Authentication auth) {
        Long userId = userService.getLoginUserByUsername(auth.getName()).getUserId();
        GroundRentalForm form = new GroundRentalForm(userId, groundId);

        model.addAttribute("form", form);

        return "ground/ground-date-selection";
    }

    @GetMapping("/ground-rental/select/{groundId}/date")
    /** 날짜가 선택될 때마다 가능한 시간 반환 */
    public ResponseEntity<?> getTimes(@PathVariable Long groundId, @RequestParam String date) {
        // 'date'를 이용해서 '가능한 시간' 정보를 계산
        List<String> availableTimes = groundRentalService.getAvailableTimes(groundId, date);

        // 계산한 정보를 JSON 형태로 응답
        Map<String, Object> response = new HashMap<>();
        response.put("availableTimes", availableTimes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/ground-rental/select/{groundId}")
    /** 예약에 필요한 정보들 확정 후 예약 실행 */
    public String rentGround(@PathVariable Long groundId, @ModelAttribute GroundRentalForm form,
                             RedirectAttributes redirectAttributes, Authentication auth) {
        // 폼에서 입력받은 날짜 및 시간을 dateTime으로 변환
        String dateTimeString = form.getSelectedDate() + "T" + form.getSelectedTime() + ":00:00";
        LocalDateTime time = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Long userId = userService.getLoginUserByUsername(auth.getName()).getUserId();

        try {
            groundRentalService.rental(userId, groundId, time);
        } catch (NoAvailableGroundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/ground-rental"; // 대여 내역으로 이동
    }

    @PostMapping("/ground-rental/{groundRentalId}/cancel")
    public String cancelRental(@PathVariable("groundRentalId") Long groundRentalId) {

        groundRentalService.cancelRental(groundRentalId);

        return "redirect:/ground-rental";
    }






}
