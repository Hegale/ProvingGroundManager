package pg.provingground.dto;

import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.GroundRental;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class GroundRentalHistory {
    private Long groundRentalId;

    /* 출력을 위해 변환된 값들 */
    private String groundName;

    private LocalDateTime startTime;

    private String canceled;

    // 취소할 수 있는 여부. 취소되지 않았으며, 예약 시행일이 내일 이후인 예약에 대해서만 "Y"
    private String cancelable;

    public GroundRentalHistory(GroundRental groundRental) {
        groundRentalId = groundRental.getGroundRentalId();
        groundName = groundRental.getGround().getName();
        startTime = groundRental.getStartTime();
        //startDate = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        canceled = groundRental.getCanceled();
        cancelable = (canceled.equals("N") && startTime.isAfter(LocalDate.now().plusDays(1).atStartOfDay()))
                ? "Y" : "N";
    }

    public GroundRentalHistory(Long groundRentalId, String groundName, LocalDateTime startTime) {
        this.groundRentalId = groundRentalId;
        this.groundName = groundName;
        this.startTime = startTime;
    }

}
