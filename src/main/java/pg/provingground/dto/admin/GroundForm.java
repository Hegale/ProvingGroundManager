package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroundForm {

    private String name;

    private String description;

    private String car_maximum; // 최대 이용 가능 차량

    private String distance;

}
