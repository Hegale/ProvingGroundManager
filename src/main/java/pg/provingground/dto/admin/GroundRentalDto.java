package pg.provingground.dto.admin;

import lombok.Getter;
import pg.provingground.domain.GroundRental;

import java.time.LocalDateTime;

@Getter
public class GroundRentalDto {
    private Long groundRentalId;

    private String groundName;

    private String username;

    private LocalDateTime startTime;
    private String canceled;

    public GroundRentalDto(GroundRental groundRental) {
        this.groundRentalId = groundRental.getGroundRentalId();
        this.groundName = groundRental.getGround() != null ? groundRental.getGround().getName() : null;
        this.username = groundRental.getUser() != null ? groundRental.getUser().getUsername() : null;
        this.startTime = groundRental.getStartTime();
        this.canceled = groundRental.getCanceled();
    }
}
