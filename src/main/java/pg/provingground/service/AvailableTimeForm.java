package pg.provingground.service;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AvailableTimeForm {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // format: 2023-12-01
    private String date;

    // format: 10, 12, 14...
    private List<Integer> times;

    public AvailableTimeForm(LocalDateTime time) {
        this.date = time.format(DATE_FORMATTER);
        this.times = new ArrayList<>();
    }

    public void addTimes(int time) {
        times.add(time);
    }

}
