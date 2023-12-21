package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(indexes = {
        @Index(name = "idx_refuel_id", columnList = "refueling_id"),
        @Index(name = "idx_time", columnList = "time"),
        @Index(name = "idx_car", columnList = "car_id"),
        @Index(name = "idx_user", columnList = "user_id"),
        @Index(name = "idx_station", columnList = "station_id")
})
public class Refuel {

    @Id @GeneratedValue
    @Column(name = "refueling_id")
    private Long refuelingId;

    private LocalDateTime time;

    private Long amount;

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
    public static Refuel createRefuel(User user, Car car, Station station, LocalDateTime time, Long amount) {
        Refuel refuel = new Refuel();
        refuel.user = user;
        refuel.car = car;
        refuel.station = station;
        refuel.time = time;
        refuel.amount = amount;
        return refuel;
    }

}