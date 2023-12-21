package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.dto.admin.UserForm;

import java.util.List;

@Entity
@Getter @Setter
@Table(indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_username_role", columnList = "username, role")
})
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

    // === 연관관계 === //

    // User가 삭제되면 관련 CarRental, Test, Refuel, GroundRental도 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CarRental> carRentals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Test> tests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Refuel> refuels;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GroundRental> groundRentals;

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
            this.password = userForm.getPassword();
        }
        if (userForm.getPhoneNum() != null) {
            //TODO: 중복 검증로직 추가?
            this.phoneNum = userForm.getPhoneNum();
        }
    }
}
