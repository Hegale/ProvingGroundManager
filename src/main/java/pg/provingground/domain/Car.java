package pg.provingground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Car {

    @Id @GeneratedValue
    private Long car_id;

    private String type;

    private String number;

}
