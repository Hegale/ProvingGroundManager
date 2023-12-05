package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
// 차종 엔티티
public class CarType {

    @Id @GeneratedValue
    @Column(name = "car_type_id")
    private Long carTypeId;

    private String name;

    private String type;

    private String description;

    private Integer displacement; // cc 단위

    @Column (name = "fuel_efficiency")
    private Double fuelEfficiency; // km/l 단위

    private String engine;

}
