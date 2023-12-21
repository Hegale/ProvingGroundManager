package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.UserRole;

@Getter @Setter
public class UserSearchForm {

    private Long userId;
    private String userIdString;

    private UserRole role;

    private String username;

    private String nickname;

    private String phoneNum;

}
