package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.dto.admin.UserForm;

import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    private UserRole role;

    @Column(unique = true) // 아이디는 유일해야 한다.
    private String username;

    private String password;

    private String nickname;

    @Column(name = "phone_num", unique = true)
    private String phoneNum;

    public static User createUser(String username, String password, String nickname, UserRole role, String phoneNum) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.nickname = nickname;
        user.role = role;
        user.phoneNum = phoneNum;
        return user;
    }

    public void edit(UserForm userForm) {
        if (userForm.getRole() != null) {
            this.role = userForm.getRole();
        }
        if (userForm.getNickname() != null) {
            this.nickname = userForm.getNickname();
        }
        if (userForm.getPassword() != null) {
            this.password = userForm.getPassword(); // 암호화 잊지 말기
        }
        if (userForm.getPhoneNum() != null) {
            this.phoneNum = userForm.getPhoneNum();
        }
    }
}
