package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    private Long user_id;

    private String id;

    private String passwd;

    private String name;

    private String phone_number;

    // 권한 아이디. 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role_id;
}
