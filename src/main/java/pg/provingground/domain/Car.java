package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(indexes = {
        @Index(name = "idx_car_id", columnList = "car_id"),
        @Index(name = "idx_car_type_id", columnList = "car_type")
})
public class Car {

    @Id @GeneratedValue
    @Column(name = "car_id")
    private Long carId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_type_id")
    private CarType type;

    private String number;

    private Long fuel; // 잔여 연료, cc단위


    public void fueling(Long amount) {
        this.fuel += amount;
    }

}
