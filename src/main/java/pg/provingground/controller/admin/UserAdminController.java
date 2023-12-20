package pg.provingground.controller.admin;

import jakarta.jws.WebParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pg.provingground.domain.User;
import pg.provingground.dto.admin.UserDto;
import pg.provingground.dto.admin.UserForm;
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

    @GetMapping("/admin/user/{userId}/edit")
    public String editPage(@PathVariable Long userId, Model model) {
        User user = userService.getLoginUserById(userId);
        UserForm userForm = new UserForm(user);

        model.addAttribute("userForm", userForm);

        return "admin/user/user-edit";
    }

    @PostMapping("/admin/user/{userId}/edit")
    public String edit(@PathVariable Long userId, @ModelAttribute UserForm userForm, Model model) {
        User user = userService.getLoginUserById(userId);

        userService.edit(user, userForm);

        return "redirect:/admin/user/list";
    }

    @DeleteMapping("/admin/user/{userId}/edit")
    /** 사용자 삭제 */
    public String userDelete(@PathVariable Long userId, Authentication auth, RedirectAttributes redirectAttributes) {
        Long loginUserId = userService.getLoginUserByUsername(auth.getName()).getUserId();
        if (userId.equals(loginUserId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "자기 자신은 삭제할 수 없습니다.");
            return "redirect:/admin/user/list";
        }
        userService.delete(userId); // 예외 캐치

        return "redirect:/admin/user/list";
    }



}
