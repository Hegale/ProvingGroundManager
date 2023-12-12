package pg.provingground.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import pg.provingground.dto.admin.UserDto;
import pg.provingground.dto.admin.UserSearchForm;
import pg.provingground.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/admin/user/list")
    public String list(@ModelAttribute UserSearchForm userSearchForm, Model model) {
        List<UserDto> userDtos = userService.getUsersByConditions(userSearchForm);

        model.addAttribute("userDtos", userDtos);
        model.addAttribute("userSearchForm", userSearchForm);

        return "admin/user/user-list";
    }

    @DeleteMapping("/admin/user/list/{userId}")
    /** 사용자 삭제 */
    public String userDelete(@PathVariable Long userId) {
        userService.delete(userId); // 예외 캐치
        return "redirect:/admin/user/list";
    }
}
