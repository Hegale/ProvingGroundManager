package pg.provingground.dto.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pg.provingground.domain.User;
import pg.provingground.domain.UserRole;

@Getter @Setter
@NoArgsConstructor
public class JoinForm {
    //@NotBlank(message = "로그인 아이디가 비어 있습니다.")
    private String username;

    private String password;
    private String passwordCheck;

    private String nickname;
    private String phoneNum;

    public User toEntity() {
        return User.createUser(
                username, password, nickname, UserRole.USER, phoneNum);
    }

    public User toEntity(String encodedPassword) {
        return User.createUser(
                username, encodedPassword, nickname, UserRole.USER, phoneNum);
    }
}
