package pg.provingground.dto.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.*;

import java.time.LocalDateTime;

@Getter @Setter
public class RefuelHistory {

    private Long refuelingId;
    private LocalDateTime time;
    private Long amount;
    private String carName;
    private String stationName;
    private FuelType fuelType;

    public RefuelHistory(Refuel refuel) {
        this.refuelingId = refuel.getRefuelingId();
        this.time = refuel.getTime();
        this.amount = refuel.getAmount();
        this.carName = refuel.getCar().getType().getName();
        this.stationName = refuel.getStation().getName();
        this.fuelType = refuel.getStation().getFuelType();
    }
}
