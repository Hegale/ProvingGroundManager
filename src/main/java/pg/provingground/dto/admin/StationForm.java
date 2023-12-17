package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.FuelType;
import pg.provingground.domain.Station;

@Getter @Setter
public class StationForm {

    private String name;

    private FuelType fuelType;

    public StationForm() {}

    public StationForm(Station station) {
        this.name = station.getName();
        this.fuelType = station.getFuelType();
    }

}
