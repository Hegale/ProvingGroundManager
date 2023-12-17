package pg.provingground.dto.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CarRentalForm {

    private Long userId;
    private Long carTypeId;

    private String selectedDate;
    private String selectedTime;

    private String userDate;

    public CarRentalForm(Long userId, Long carTypeId) {
        this.userId = userId;
        this.carTypeId = carTypeId;
    }
}
