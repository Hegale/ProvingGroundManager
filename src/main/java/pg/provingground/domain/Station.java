package pg.provingground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Station {

    @Id @GeneratedValue
    private Long station_id;

    private String name;

}
