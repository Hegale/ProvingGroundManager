package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
