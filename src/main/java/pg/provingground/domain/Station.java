package pg.provingground.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.dto.admin.StationForm;

import java.util.List;

@Entity
@Getter @Setter
public class Station {

    @Id @GeneratedValue
    @Column(name = "station_id")
    private Long stationId;

    @Column(unique = true)
    private String name;

    private FuelType fuelType;

    // === 연관관계 === //
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Refuel> refuel;

    public static Station createStation(String name, FuelType fuelType) {
        Station station = new Station();
        station.name = name;
        station.fuelType = fuelType;
        return station;
    }

    public void edit(StationForm stationForm) {
        if (stationForm.getName() != null) {
            this.name = stationForm.getName();
        }
        if (stationForm.getFuelType() != null) {
            this.fuelType = stationForm.getFuelType();
        }
    }

}
