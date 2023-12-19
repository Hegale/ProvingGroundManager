package pg.provingground.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {

    @Autowired private CarRepository carRepository;
    private CarType carType;
    private Car car;

    @BeforeEach
    void setup() {
        carType = new CarType();
        car = Car.createCar(carType, "92고 3761");
    }

    @Test
    public void 차량번호로_검색_성공() throws Exception {
        //given
        String carNumber = "92고 3761";
        carRepository.save(car);

        //when
        List<Car> result = carRepository.findByNumber(carNumber);

        //then
        assertFalse(result.isEmpty());
    }

    @Test
    public void 차량번호로_검색_실패() throws Exception {
        //given
        String carNumber = "92고 3762";
        carRepository.save(car);

        //when
        List<Car> result = carRepository.findByNumber(carNumber);

        //then
        assertTrue(result.isEmpty());
    }





}