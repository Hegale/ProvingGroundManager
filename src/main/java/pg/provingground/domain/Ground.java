package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "ground", indexes = @Index(name = "idx_ground_id", columnList = "ground_id"))
public class Ground {

    @Id @GeneratedValue
    @Column(name = "ground_id")
    private Long groundId;

    private String name;

    private Long user_maximum;

    private Long distance;

    // private Double Coordinate; ; 일단 보류. JTS 사용할수도 있음
    private int fuel_consumption;

}
