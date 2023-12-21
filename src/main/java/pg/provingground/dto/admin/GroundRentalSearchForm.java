package pg.provingground.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class GroundRentalSearchForm {
    private Long groundRentalId;
    private String groundRentalIdString;

    private String groundName;

    private String startDate;
    private String endDate;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String canceled;

    // === 유저로 검색 === //
    private String username;
}
