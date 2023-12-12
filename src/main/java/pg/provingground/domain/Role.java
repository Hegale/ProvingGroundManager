package pg.provingground.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
// TODO: 삭제
public class Role {

    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long roleId;

    @Column(unique = true)
    private String name;
}
