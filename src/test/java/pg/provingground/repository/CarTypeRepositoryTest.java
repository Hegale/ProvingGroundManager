package pg.provingground.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.CarType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CarTypeRepositoryTest {

    @Autowired CarTypeRepository carTypeRepository;

    @Test
    @Rollback(false)
    public void buildCarTypeTestData() {

        List<CarType> carTypes = new ArrayList<CarType>();
        List<String> names = List.of(
                "더 뉴 아반떼", "쏘나타 디 엣지", "디 올 뉴 그랜저",
                "디 올 뉴 싼타페", "베뉴", "디 올 뉴 코나", "투싼",
                "팰리세이드", "아이오닉 6", "넥쏘");
        List<String> types = List.of(
                "승용", "승용", "승용", "SUV", "SUV", "SUV", "SUV", "SUV", "수소/전기차", "수소/전기차");
        List<String> engines = List.of(
                "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "디젤", "디젤", "전기", "수소");
        for (int i = 0; i < 10; ++i) {
            CarType carType = new CarType();
            carType.setName(names.get(i));
            carType.setType(types.get(i));
            carType.setEngine(engines.get(i));
            carTypeRepository.save(carType);
        }
    }
}