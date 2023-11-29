package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class GroundRental {

    @Id @GeneratedValue
    @Column(name = "ground_rental_id")
    private Long groundRentalId;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime start_time;

    private String returned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ground_id")
    private Ground ground;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
