package pg.provingground.dto.admin;

import lombok.Getter;
import pg.provingground.domain.User;
import pg.provingground.domain.UserRole;

@Getter
public class UserDto {
    private Long userId;
    private UserRole role;
    private String username;
    private String nickname;
    private String phoneNum;

    public UserDto(Long userId, UserRole role, String username, String nickname, String phoneNum) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
    }

}
