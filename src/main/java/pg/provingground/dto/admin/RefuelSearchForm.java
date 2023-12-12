package pg.provingground.dto.admin;


import lombok.Getter;
import pg.provingground.domain.FuelType;

@Getter
public class RefuelSearchForm {

    private Long refuelId;

    private String stationName;

    private FuelType fuelType;

    private String carName;

    private String carNumber;

    private String startDate;

    private String endDate;

    private String amount;

}
