package pg.provingground.dto.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.CarRental;
import pg.provingground.domain.GroundRental;
import pg.provingground.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class TestSearchForm {

    private Long testId;
    private String testIdString;

    private String type; // 시험종류

    private String startDate;
    private String endDate;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String partners; // 별도의 partner를 두어 여러 협력사를 관리하는 것 고려하기

    private String title;

    private String groundName;

    private String username;

}
