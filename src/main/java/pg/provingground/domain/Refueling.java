package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Getter
public class Refueling {

    @Id @GeneratedValue
    private Long refueling_id;

    private Timestamp fuel_time;

    private int fuel_amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Station station_id;

}
