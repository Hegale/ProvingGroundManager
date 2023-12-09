package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.User;
import pg.provingground.dto.CarRentalHistory;
import pg.provingground.dto.MessageDto;
import pg.provingground.repository.UserRepository;
import pg.provingground.service.AvailableTimeForm;
import pg.provingground.service.CarRentalService;
import pg.provingground.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;
    private final UserService userService;

    @GetMapping("/car-rental")
    /** 차량 대여 내역 */
    public String list(Model model, Authentication auth) {
        // 해당 유저의 차량 대여 내역을 보여준다.
        User user = userService.getLoginUserByUsername(auth.getName());
        List<CarRentalHistory> rentals = carRentalService.findRentalHistory(user);
        model.addAttribute("rentals", rentals);
        return "car/car-rent-history";
    }

    @PostMapping("/car-rental/{carRentalId}/return")
    /** 차량 대여 반납 및 취소 */
    public String returnRental(@PathVariable("carRentalId") Long carRentalId, Authentication auth, RedirectAttributes redirectAttributes) {
        // 정보 요청자가 해당 기록의 주인이 아닐 시
        if (!carRentalService.isOwnerMatched(carRentalId, userService.getLoginUserByUsername(auth.getName()))) {
            redirectAttributes.addFlashAttribute("errorMessage", "해당 권한이 없습니다!");
            return "redirect:/car-rental";
        }
        carRentalService.cancelRental(carRentalId);
        return "redirect:/car-rental";
    }

    @GetMapping("/car-rental/select/{carTypeId}")
    /** 차량 선택 후 날짜 선택 페이지 */
    public String selectDate(@PathVariable Long carTypeId, Model model, Authentication auth) {
        Long userId = userService.getLoginUserByUsername(auth.getName()).getUserId();
        CarRentalForm form = new CarRentalForm(userId, carTypeId);
        //List<AvailableTimeForm> times = carRentalService.getAvailableTimeForms(carTypeId);

        // 차량의 타입 전달
        model.addAttribute("type", carRentalService.findType(carTypeId));
        // 선택한 날짜와 시간을 입력받아 올 폼 전달
        model.addAttribute("form", form);
        // TODO: 불가능한 '날짜'만 우선적으로 보내기! 선택한 날짜에 대한 시간정보는 아래에서 할거임
        // TODO: 불가능한 날짜 및 시간 비활성화 -> ajax 힘드니까 '날짜 선택' 뭐 이정도로 바꾸기...
        //model.addAttribute("availableTimes", times);

        return "car/car-date-selection";
    }

    @PostMapping("/car-rental/select/{carTypeId}")
    /** 대여에 필요한 정보들 확정 후 대여 시행 */
    public String rentCar(@PathVariable Long carTypeId, @ModelAttribute CarRentalForm form, Authentication auth) {
        // 폼에서 입력받은 날짜 및 시간을 dateTime으로 변환
        String dateTimeString = form.getSelectedDate() + "T" + form.getSelectedTime() + ":00:00";
        LocalDateTime time = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Long carId = carRentalService.getSchedulableCar(carTypeId, time);
        Long userId = userService.getLoginUserByUsername(auth.getName()).getUserId();

        carRentalService.rental(userId, carId, time);

        return "redirect:/car-rental"; // 대여 내역으로 이동
    }

    @GetMapping("/car-rental/select/{carTypeId}/date")
    /** 날짜가 선택될 때마다 가능한 시간 반환 */
    public ResponseEntity<?> getTimes(@PathVariable Long carTypeId, @RequestParam String date) {
        // 'date'를 이용해서 '가능한 시간' 정보를 계산
        List<String> availableTimes = carRentalService.getAvailableTimes(carTypeId, date);

        // 계산한 정보를 JSON 형태로 응답
        Map<String, Object> response = new HashMap<>();
        response.put("availableTimes", availableTimes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
