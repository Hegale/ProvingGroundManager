package pg.provingground.dto.admin;

import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.CarType;

@Getter @Setter
public class CarForm {

    private String type;

    private String number;

    private String fuelAmount;

    public CarForm() {}

    public CarForm(String number, String fuelAmount) {
        this.number = number;
        this.fuelAmount = fuelAmount;
    }

}
