package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

import java.awt.*;

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

    // private Point location;

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
