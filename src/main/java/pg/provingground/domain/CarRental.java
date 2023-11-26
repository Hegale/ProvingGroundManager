package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Getter
public class CarRental {

    @Id @GeneratedValue
    private Long car_rental_id;

    private Timestamp start_time;

    private String returned;

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user_id;
}
