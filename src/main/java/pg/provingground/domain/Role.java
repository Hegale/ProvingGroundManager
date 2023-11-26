package pg.provingground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Role {

    @Id @GeneratedValue
    private Long role_id;

    private String name;
}
