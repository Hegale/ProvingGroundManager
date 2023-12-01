package pg.provingground.repository;

import org.springframework.transaction.annotation.Transactional;
import pg.provingground.domain.Car;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class CarRepositoryTest {
    public void 차량_테스트데이터_생성() throws Exception {

        List<Car> cars = new ArrayList<Car>();
        List<String> names = List.of(
                "더 뉴 아반떼", "쏘나타 디 엣지", "디 올 뉴 그랜저",
                "디 올 뉴 싼타페", "베뉴", "디 올 뉴 코나", "투싼",
                "팰리세이드", "아이오닉 6", "넥쏘");
        List<String> types = List.of(
                "승용", "승용", "승용", "SUV", "SUV", "SUV", "SUV", "SUV", "수소/전기차", "수소/전기차");
        List<String> engines = List.of(
                "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "가솔린", "디젤", "디젤", "전기", "수소");
        for (int i = 0; i < 10; ++i) {
            Car car = new Car();
            car.setNumber("12가 3456");
            car.setEngine(engines.get(i));
        }


    }
}