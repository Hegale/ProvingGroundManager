package pg.provingground.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import pg.provingground.domain.*;
import pg.provingground.dto.admin.CarDto;
import pg.provingground.dto.admin.CarForm;
import pg.provingground.dto.admin.TestDto;
import pg.provingground.dto.form.TestForm;
import pg.provingground.dto.history.CarRentalHistory;
import pg.provingground.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TestServiceTest {

    @Mock
    private GroundRentalRepositoryImpl groundRentalRepository;
    @Mock
    private CarRentalRepository carRentalRepository;
    @Mock
    private TestRepository testRepository;
    @InjectMocks
    private TestService testService;

    private Test mockTest;
    private GroundRental groundRental;
    private Ground ground;
    private Car car;
    private CarRental carRental;
    private CarType carType;

    @BeforeEach
    void setup() {
        car = mock(Car.class);
        carRental = mock(CarRental.class);
        carType = mock(CarType.class);
        ground = mock(Ground.class);
        groundRental = mock(GroundRental.class);
    }

    @org.junit.jupiter.api.Test
    void 시험내역_삭제() {
        // given
        Long testId = 1L;
        Test mockTest = mock(Test.class);
        when(testRepository.findOne(testId)).thenReturn(mockTest);

        // when
        testService.delete(testId);

        // then
        verify(testRepository).delete(mockTest);
    }
}