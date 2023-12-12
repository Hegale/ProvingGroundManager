package pg.provingground.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class CarRentalSearchForm {

    // === CarRentalId로 검색 === //
    private Long carRentalId;

    // === 차종으로 검색 === //
    private String carName;

    // === 차량으로 검색 === //
    private Long carNumber; // 굳이?

    // === 날짜 범위로 검색 === //
    private LocalDate start;
    private LocalDate end;

    // === 유저로 검색 === //
    private String username;

}
