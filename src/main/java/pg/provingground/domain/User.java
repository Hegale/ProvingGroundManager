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

    /*
    // 권한 아이디. 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
     */

    private String role;

    @Column(unique = true) // 아이디는 유일해야 한다.
    private String id;

    private String password;

    private String name;

    @Column(name = "phone_num", unique = true) // 이속성 그냥 없내느것도 생각해보기
    private String phoneNum;

    public static User createUser(String id, String password, String name, String phoneNum) {
        User user = new User();
        user.id = id;
        user.password = password;
        user.name = name;
        user.phoneNum = phoneNum;
        // TODO: Role도 추가
        return user;
    }
}
