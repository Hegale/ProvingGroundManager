package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import pg.provingground.domain.UserRole;

@Getter
public class UserSearchForm {

    private Long userId;

    private UserRole role;

    private String username;

    private String nickname;

    private String phoneNum;

}
