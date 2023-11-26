package pg.provingground.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Ground {

    @Id @GeneratedValue
    private Long ground_id;

    private String name;

    private Long user_maximum;

    private Long distance;

    // private Double Coordinate; ; 일단 보류. JTS 사용할수도 있음
    private int fuel_consumption;

}
