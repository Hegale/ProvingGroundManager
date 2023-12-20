package pg.provingground.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilsTest {

    @Test
    void 날짜의_시작시간으로_변경() {
        //given
        String startDate = "2024-01-01";
        LocalDateTime dateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

        //when
        LocalDateTime result = DateTimeUtils.convertToStartOfDay(startDate);

        //then
        assertEquals(result, dateTime);
    }

    @Test
    void 날짜의_종료시간으로_변경() {
        //given
        String endDate = "2024-01-01";
        LocalDateTime dateTime = LocalDateTime.of(2024, 1, 1, 23, 59, 59, 999999999);

        //when
        LocalDateTime result = DateTimeUtils.convertToEndOfDay(endDate);

        //then
        assertEquals(result, dateTime);
    }

}