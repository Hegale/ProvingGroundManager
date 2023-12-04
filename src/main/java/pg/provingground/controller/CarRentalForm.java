package pg.provingground.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CarRentalForm {

    private Long userId;
    private Long carTypeId;

    private String selectedDate;
    private String selectedTime;

    public CarRentalForm(Long userId, Long carTypeId) {
        this.userId = userId;
        this.carTypeId = carTypeId;
    }
}
