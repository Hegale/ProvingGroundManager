package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pg.provingground.domain.User;
import pg.provingground.dto.JoinForm;
import pg.provingground.dto.LoginForm;
import pg.provingground.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserContoller {

    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, Authentication auth) {
        if (auth != null) {
            // 현재 유저 받아오기
            User loginUser = userService.getLoginUserByUsername(auth.getName());
            if (loginUser != null) {
                // 로그인된 유저라면, 닉네임 받아오기. TODO: 받아온 유저이름 화면에 띄우기
                model.addAttribute("nickname", loginUser.getNickname());
            }
        }
        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinForm", new JoinForm());
        return "user/signup";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute JoinForm joinForm, BindingResult bindingResult, Model model) {
        // username 중복체크
        if (userService.checkLogInDuplicate(joinForm.getUsername())) {
            bindingResult.addError(new FieldError("joinForm", "username", "중복된 아이디입니다."));
        }

        // nickname 중복체크
        if (userService.checkNicknameDuplicate(joinForm.getNickname())) {
            bindingResult.addError(new FieldError("joinForm", "nickname", "중복된 비밀번호입니다."));
        }

        // password와 passwordCheck가 같은지 체크
        if (!joinForm.getPassword().equals(joinForm.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinForm", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if (bindingResult.hasErrors()) {
            return "user/signup";
        }

        userService.join2(joinForm);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "user/login";
    }

    /** 유저 마이페이지 */
    @GetMapping("/info")
    public String userInfo(Model model, Authentication auth) {
        User loginUser = userService.getLoginUserByUsername(auth.getName());

        if (loginUser == null) {
            return "redirect:/security-login/login";
        }

        model.addAttribute("user", loginUser);
        return "user/info";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin"; //왜 검증안해?
    }

}
