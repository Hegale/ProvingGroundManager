package pg.provingground.dto;

import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.GroundRental;

@Getter @Setter
public class GroundRentalHistory {
    private Long groundRentalId;

    /* 출력을 위해 변환된 값들 */
    private String groundName;

    private String startTime;

    public GroundRentalHistory(GroundRental groundRental) {
        groundRentalId = groundRental.getGroundRentalId();
        groundName = groundRental.getGround().getName();
        startTime = groundRental.getStartTime().toString();
    }

}
