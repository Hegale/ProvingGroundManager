package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import lombok.Getter;
import pg.provingground.domain.FuelType;

@Getter
public class StationForm {

    private String name;

    private FuelType fuelType;

}
