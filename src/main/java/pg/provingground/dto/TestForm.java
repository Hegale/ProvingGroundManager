package pg.provingground.dto;

import lombok.Getter;
import lombok.Setter;
import pg.provingground.domain.Car;
import pg.provingground.domain.Ground;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class TestForm {
    String testDate;

    Long groundRentalId;
    List<Long> carRentalIds;

    String partners; // TODO: 추후 협력사를 테이블로 분리하는 것 고려

    String title;
    String contents;

}
