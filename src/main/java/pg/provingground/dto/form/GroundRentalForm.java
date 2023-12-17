package pg.provingground.dto.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroundRentalForm {
    private Long userId;
    private Long groundId;

    private String selectedDate;
    private String selectedTime;

    public GroundRentalForm(Long userId, Long groundId) {
        this.userId = userId;
        this.groundId = groundId;
    }
}
