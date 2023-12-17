package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.Ground;

@Getter @Setter
public class GroundForm {

    private String name;

    private String description;

    private String car_maximum; // 최대 이용 가능 차량

    private String distance;

    public GroundForm() {}

    public GroundForm(Ground ground) {
        this.name = ground.getName();
        this.description = ground.getDescription();
        this.car_maximum = String.valueOf(ground.getCar_maximum());
        this.distance = String.valueOf(ground.getDistance());
    }

}
