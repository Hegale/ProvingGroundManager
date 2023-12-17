package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.User;
import pg.provingground.domain.UserRole;

@Getter @Setter
public class UserForm {
    private UserRole role;

    private String username;

    private String password;

    private String nickname;

    private String phoneNum;

    public UserForm(User user) {
        this.role = user.getRole();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.phoneNum = user.getPhoneNum();
    }
}
