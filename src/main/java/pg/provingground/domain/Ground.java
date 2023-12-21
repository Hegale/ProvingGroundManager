package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;
import pg.provingground.dto.admin.GroundForm;

import java.awt.*;

@Entity
@Getter @Setter
@Table(indexes = @Index(name = "idx_ground_id", columnList = "ground_id"))
public class Ground {

    @Id @GeneratedValue
    @Column(name = "ground_id")
    private Long groundId;

    @Column(unique = true) // 아이디는 유일해야 한다.
    private String name;

    private String description;

    private int car_maximum; // 최대 이용 가능 차량

    private int distance;

    public static Ground createGround(String name, String description, int car_maximum, int distance) {
        Ground ground = new Ground();
        ground.name = name;
        ground.description = description;
        ground.car_maximum = car_maximum;
        ground.distance = distance;
        return ground;
    }

    public void edit(GroundForm groundForm) {
        if (!groundForm.getName().isEmpty()) {
            this.name = groundForm.getName();
        }
        if (!groundForm.getDescription().isEmpty()) {
            this.description = groundForm.getDescription();
        }
        if (groundForm.getCar_maximum() != null) {
            this.car_maximum = Integer.parseInt(groundForm.getCar_maximum());
            if (car_maximum <= 0) {
                throw new NumberFormatException("유효하지 않은 최대 차량 대수입니다.");
            }
        }
        if (groundForm.getDistance() != null) {
            this.distance = Integer.parseInt(groundForm.getDistance());
            if (distance <= 0) {
                throw new NumberFormatException("유효하지 않은 주행거리입니다.");
            }
        }
    }

}
