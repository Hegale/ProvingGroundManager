package pg.provingground.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.dto.admin.StationForm;

@Entity
@Getter @Setter
public class Station {

    @Id @GeneratedValue
    @Column(name = "station_id")
    private Long stationId;

    @Column(unique = true)
    private String name;

    private FuelType fuelType;

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
