package pg.provingground.dto.admin;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.FuelType;

@Getter @Setter
public class StationForm {

    private String name;

    private FuelType fuelType;

}
