package pg.provingground.dto.history;

import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.CarRental;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class TestHistory {

    private Long testId;

    private LocalDateTime dateTime;

    private String groundName;

    private int carCount; // 사용 차량 대수

    private String partners;

    private String title;

    public TestHistory(Long testId, LocalDateTime dateTime, String groundName, int carCount, String partners, String title) {
        this.testId = testId;
        this.dateTime = dateTime;
        this.groundName = groundName;
        this.carCount = carCount;
        if (partners != null) {
            this.partners = partners;
        }
        this.title = title;
    }

}
