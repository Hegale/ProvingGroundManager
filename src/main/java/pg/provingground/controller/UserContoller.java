package pg.provingground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserContoller {

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

}
