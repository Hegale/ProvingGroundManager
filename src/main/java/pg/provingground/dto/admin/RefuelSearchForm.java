package pg.provingground.dto.admin;


import lombok.Getter;

@Getter
public class RefuelSearchForm {

    private Long refuelId;

    private String carName;

    private String carNumber;

    private String fuelType;

    private String stationName;

    private String startDate;

    private String endDate;

    private String amount;

}
