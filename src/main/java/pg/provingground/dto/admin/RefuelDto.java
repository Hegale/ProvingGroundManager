package pg.provingground.dto.admin;

import jakarta.persistence.*;
import lombok.Getter;
import pg.provingground.domain.*;

import java.time.LocalDateTime;

@Getter
public class RefuelDto {

    private Long refuelingId;

    private LocalDateTime time;

    private Long amount;

    private String carName;

    private String carNumber;

    private FuelType fuelType;

    private String stationName;

    public RefuelDto() {}
    public RefuelDto(Long refuelingId, String carName, String carNumber, FuelType fuelType, String stationName, LocalDateTime time, Long amount) {
        this.refuelingId = refuelingId;
        this.carName = carName;
        this.carNumber = carNumber;
        this.fuelType = fuelType;
        this.stationName = stationName;
        this.time = time;
        this.amount = amount;
    }
}
