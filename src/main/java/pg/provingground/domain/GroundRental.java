package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Getter
public class GroundRental {

    @Id @GeneratedValue
    private Long ground_rental_id;

    private Timestamp start_time;

    private String returned;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ground ground_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user_id;
}
