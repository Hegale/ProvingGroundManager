package pg.provingground.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pg.provingground.domain.CarType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarTypeRepositoryTest {
    @Autowired private CarTypeRepository carTypeRepository;

    private CarType carType;

    @BeforeEach
    void setup() {
        carType = new CarType();
        carType.setType("승용");
        carType.setEngine("가솔린");
        carType.setName("더 뉴 아반떼");
    }

    @Test
    public void 이름으로_검색() throws Exception {
        //given
        carTypeRepository.save(carType);
        String name = "뉴";

        //when
        List<CarType> result = carTypeRepository.findByName(name);

        //then
        assertFalse(result.isEmpty());

    }

    @Test
    public void 조건으로_검색() throws Exception {
        //given
        carTypeRepository.save(carType);
        String engine = "가솔린";
        String type = "승용";

        //when
        List<CarType> result = carTypeRepository.findByCondition(engine, type, null);

        //then
        assertFalse(result.isEmpty());

    }

}