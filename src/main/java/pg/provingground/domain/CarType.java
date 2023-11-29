package pg.provingground.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
// 차종 엔티티
public class CarType {

    @Id @GeneratedValue
    @Column(name = "car_type_id")
    private Long CarTypeId;

    private String name;

    private String type;

    private String engine;

}
