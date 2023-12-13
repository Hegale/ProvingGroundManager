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

    private String description;

    private int car_maximum; // 최대 이용 가능 차량

    private int distance;

    // private Double Coordinate; ; 일단 보류. JTS 사용할수도 있음
    //private int fuel_consumption;

    public static Ground createGround(String name, String description, int car_maximum, int distance) {
        Ground ground = new Ground();
        ground.name = name;
        ground.description = description;
        ground.car_maximum = car_maximum;
        ground.distance = distance;
        return ground;
    }

}
