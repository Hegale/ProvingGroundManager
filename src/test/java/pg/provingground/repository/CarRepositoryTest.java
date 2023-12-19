package pg.provingground.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import pg.provingground.domain.Car;
import pg.provingground.domain.CarType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarRepositoryTest {

    @Autowired private CarRepository carRepository;
    @Autowired private CarTypeRepository carTypeRepository;
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
        carTypeRepository.save(carType);
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
        carTypeRepository.save(carType);
        carRepository.save(car);

        //when
        List<Car> result = carRepository.findByNumber(carNumber);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void 차종으로_검색() throws Exception {
        //given
        carTypeRepository.save(carType);
        carRepository.save(car);
        List<CarType> carTypes = List.of(carType);

        //when
        List<Car> result = carRepository.findByCarTypes(carTypes);

        //then
        assertFalse(result.isEmpty());

    }
    
    @Test
    public void 차량번호_중복_확인() throws Exception {
        //given
        carTypeRepository.save(carType);
        carRepository.save(car);
        String carNumber = "92고 3762";

        //when
        //then
        assertTrue(carRepository.isDuplicateCarNumber(carNumber));
    }

    @Test
    public void 차종별_차량대수() throws Exception {
        //given
        // ApplicationContext를 통해 CarTypeRepository 빈을 가져옴
        carTypeRepository.save(carType);
        Car car2 = Car.createCar(carType, "31바 8241");
        carRepository.save(car);
        carRepository.save(car2);

        //when
        //then
        assertEquals(carRepository.countCarsPerCarType(1L), 2);

    }

}