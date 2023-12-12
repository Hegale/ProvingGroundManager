package pg.provingground.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroundRentalSearchForm {
    private Long groundRentalId;

    private String groundName;

    private String startDate;
    private String endDate;

    private String canceled;

    // === 유저로 검색 === //
    private String username;
}
