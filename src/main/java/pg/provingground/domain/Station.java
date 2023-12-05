package pg.provingground.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Station {

    @Id @GeneratedValue
    @Column(name = "station_id")
    private Long stationId;

    @Column(unique = true)
    private String name;

    public static Station createStation(String name) {
        Station station = new Station();
        station.name = name;
        return station;
    }

}
