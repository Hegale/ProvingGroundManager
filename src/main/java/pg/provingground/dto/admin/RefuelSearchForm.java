package pg.provingground.dto.admin;


import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import pg.provingground.domain.FuelType;

import java.time.LocalDateTime;

@Getter @Setter
public class RefuelSearchForm {

    private Long refuelId;

    private String stationName;

    private FuelType fuelType;

    private String carName;

    private String carNumber;

    private String startDate;
    private String endDate;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String amount;

}
