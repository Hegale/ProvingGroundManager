package pg.provingground.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.GroundRentalSearchForm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroundRentalRepositoryTest {

    @Autowired private GroundRentalRepository groundRentalRepository;

    private User user;
    private LocalDateTime time;
    private Ground ground;
    private GroundRental groundRental;

    @BeforeEach
    void setup() {
        user = User.createUser("tester", "1234", "테스터", UserRole.USER, "010-1234-5678");
        ground = new Ground();
        time = LocalDateTime.now().with(LocalTime.of(10, 0));
        groundRental = GroundRental.createGroundRental(user, ground, time);
    }
    
    @Test
    public void 특정날짜_예약건_확인() throws Exception {
        //given
        LocalDate date = LocalDate.now();
        
        //when
        Map<LocalTime, Long> result = groundRentalRepository.findAvailableTimesCount(1L, date);
        
        //then
        
    }

    @Test
    public void 조건으로_검색() throws Exception {
        //given
        GroundRentalSearchForm searchForm = new GroundRentalSearchForm();

        //when
        List<GroundRental> result = groundRentalRepository.searchGroundRentals(searchForm);

        //then

    }

}