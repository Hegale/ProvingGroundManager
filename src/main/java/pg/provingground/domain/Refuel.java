package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Refuel {

    @Id @GeneratedValue
    @Column(name = "refueling_id")
    private Long refuelingId;

    private LocalDateTime time;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;


    // === 생성 메서드 === //
    /** 새 refuel 객체 생성 */
    public static Refuel createRefuel(User user, Car car, Station station, LocalDateTime time, int amount) {
        Refuel refuel = new Refuel();
        refuel.user = user;
        refuel.car = car;
        refuel.station = station;
        refuel.time = time;
        refuel.amount = amount;
        return refuel;
    }

}