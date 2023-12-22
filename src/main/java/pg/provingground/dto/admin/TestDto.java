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
public class TestDto {
    private Long testId;

    private LocalDateTime dateTime;

    //private String type; // 시험종류

    private String partners; // 별도의 partner를 두어 여러 협력사를 관리하는 것 고려하기

    private String title;

    private String contents;

    private String groundName;

    private String username;

    public TestDto() {}

    public TestDto(Long testId, LocalDateTime dateTime, String type, String partners, String title, String contents, String groundName, String username) {
        this.testId = testId;
        this.dateTime = dateTime;
        //this.type = type;
        this.partners = partners;
        this.title = title;
        this.contents = contents;
        this.groundName = groundName;
        this.username = username;
    }
}
