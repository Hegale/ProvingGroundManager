package pg.provingground.dto;


import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarRental;

import java.time.LocalDateTime;

@Getter @Setter
/** 차량 대여 내역의 목록에 넘겨주기 위한 dto */
public class CarRentalHistory {

    private Long carRentalId;

    private String returned;

    /* 출력을 위해 변환된 값들 */
    private String carName;

    private String startTime;

    // TODO: fetch join 고려
    public CarRentalHistory(CarRental carRental) {
        carRentalId = carRental.getCarRentalId();
        returned = carRental.getReturned();
        startTime = carRental.getStartTime().toString();
        carName = carRental.getCar().getType().getName();
    }

}
