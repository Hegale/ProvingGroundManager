package pg.provingground.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class CarRentalDto {

    private Long carRentalId;

    private String carName;

    private String carNumber;

    private String username;

    // === CarRental의 멤버들 === //
    private LocalDateTime startTime;
    private String returned;

    // 이 생성자를 추가합니다
    public CarRentalDto(Long carRentalId, String carName, String carNumber, String username, LocalDateTime startTime, String returned) {
        this.carRentalId = carRentalId;
        this.carName = carName;
        this.carNumber = carNumber;
        this.username = username;
        this.startTime = startTime;
        this.returned = returned;
    }

}
