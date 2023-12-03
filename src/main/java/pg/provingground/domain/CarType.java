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

    private String description;

    private Integer displacement; // cc 단위

    @Column (name = "fuel_efficiency")
    private Double fuelEfficiency; // km/l 단위

    private String engine;

}
