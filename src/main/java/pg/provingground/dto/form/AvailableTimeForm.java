package pg.provingground.dto.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
// TODO: 삭제
public class AvailableTimeForm {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // format: 2023-12-01
    private String date;

    // format: 10, 12, 14...
    private List<Integer> times;

    private String selectedDate;

    public AvailableTimeForm(LocalDateTime time) {
        this.date = time.format(DATE_FORMATTER);
        this.times = new ArrayList<>();
    }

    public void addTimes(int time) {
        times.add(time);
    }

}
